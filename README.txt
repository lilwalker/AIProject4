#########
# FILES #
#########
AIProject4/ contains an eclipse Java project with the source
sample_outputs/ contains sample outputs on input6.txt for all 4 CSP methods implemented
lgwalker_kkumykov_csp.jar is a precompiled binary, compiled in java18
Report.docx - the report

###########
# RUNNING #
###########
`java -jar lgwalker_kkumykov_csp.jar <input.txt> [search type]`
search type is an optional argument:
0 - Just run backtracking search
1 - Backtracking + Heuristics
2 - Backtracking + Heuristics + Forward Checking (Default)
3 - MinConflicts (Note: because of local minima, may need to run multiple times)

Backtracking search variants will output results to “output_date”, and a log to “log_file_date”, as well as printing results to stdout
MinConflicts will only print to stdout

