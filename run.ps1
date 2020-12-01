# .SYNOPSIS
#   Run the solution for a given year and day.
# .DESCRIPTION
#   Runs the solution for the given year and day. It will delegate to the "year" script to actually do
#   the execution. This allows us to ignore language-specific stuff in this script.
#   Required input files should be present already, and the script for a day should output two lines to
#   stdout, representing the solution for the first and second task of the day.
#   The final script will receive a command line param "first" or "second" or nothing.
#   If nothing, both tasks must be run, otherwise only the one as specified.
Param(
# The year you want to execute from.
    [Parameter(Mandatory)][Int]$year,
# The day you want to execute.
    [Parameter(Mandatory)][Int]$day,
# Which section of the day to run.
    [String][ValidateSet('first', 'second')]$part = ""
)

$now = Get-Date -DisplayHint Date
$requested = Get-Date -Year $year -Month 12 -Day $day -DisplayHint Date
$rs = $requested.ToString("d")
if ($now -lt $requested)
{
    $ns = $now.ToString("d")
    Write-Warning "The requested date of $rs is after today ($ns). This won't work."
}

Write-Output "Running solution for $rs..."
if ($part -eq "")
{
    Write-Verbose "Requested all tasks."
}
else
{
    Write-Verbose "Requested $part task."
}

# Get the path to script responsible for the requested year and/or day.
# You can override the yearly script by providing one per day.

function ToRoman($integer)
{
    $romans = [ordered]@{
        1000 = "m";
        900 = "cm";
        500 = "d";
        400 = "cd";
        100 = "c";
        90 = "xc";
        50 = "l";
        40 = "xl";
        10 = "x";
        9 = "ix";
        5 = "v";
        4 = "iv";
        1 = "i"
    }
    
    write "HALLO"

    $result = ""
    while ($integer -gt 0)
    {
        write $result
        foreach($h in $romans.GetEnumerator()) {
            while ($integer -gt $h.Key)
            {
                $result = $result + $h.Value
                $integer = $integer - $h.Key
                write $result
            }
            write $result
        }
        write $result
    }
    return $result
}

$rr = ToRoman($year)
Write-Output $rr