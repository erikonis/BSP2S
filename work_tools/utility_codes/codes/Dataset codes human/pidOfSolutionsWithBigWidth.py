import json
import csv
import sys

csv.field_size_limit(sys.maxsize)

"""
This script analyzes solution metadata and identifies problem IDs (p_id) associated with solutions
that have large widths (e.g., 128, 256, 384, 512). It loads solution information from 'info.json' and
maps solution IDs (s_id) to problem IDs using 'Java_metadata.csv'. For each specified width, it prints
out the number of unique problem IDs and their list. Useful for understanding which problems have
solutions with large widths in the dataset.
"""


INFO_JSON = "info.json"
CSV_FILE = "Java_metadata.csv"
WIDTHS = {512, 384, 256, 128}

# Load info.json
with open(INFO_JSON, "r", encoding="utf-8") as f:
    info = json.load(f)

# Find solution ids with the desired widths (strip extension)
width_to_sids = {w: set() for w in WIDTHS}
for sid_with_ext, meta in info.items():
    sid = sid_with_ext.rsplit(".", 1)[0]  # Remove extension
    w = meta.get("width")
    if w in WIDTHS:
        width_to_sids[w].add(sid)

# Map s_id to p_id from CSV
sid_to_pid = {}
with open(CSV_FILE, "r", encoding="utf-8") as f:
    reader = csv.DictReader(f)
    for row in reader:
        sid = row["s_id"]
        pid = row["p_id"]
        sid_to_pid[sid] = pid

# For each width, collect unique p_ids
width_to_pids = {}
for w, sids in width_to_sids.items():
    pids = {sid_to_pid[sid] for sid in sids if sid in sid_to_pid}
    width_to_pids[w] = pids

# Print results
for w in sorted(WIDTHS, reverse=True):
    print(f"Width {w}: {len(width_to_pids[w])} unique p_id(s)")
    print(f"p_ids: {sorted(width_to_pids[w])}\n")
