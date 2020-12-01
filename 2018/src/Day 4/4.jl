#=
4:
- Julia version: 1.0.2
- Author: Niko
- Date: 2018-12-04
=#

using Dates
import Base: parse

abstract type GuardInfo end

struct GuardChange <: GuardInfo
    date::DateTime
    id::Int
end
struct Wakeup <: GuardInfo
    date::DateTime
end
struct Sleep <: GuardInfo
    date::DateTime
end

function parseLine(line) :: GuardInfo
    regex = r"^\[(\d{4}-\d{2}-\d{2} \d{2}:\d{2})\] (.*)$"
    matched = match(regex, line)
    if matched === nothing
        println("Did not match $line")
    end
    date, action = match(regex, line).captures
    date = DateTime(date,"yyyy-mm-dd HH:MM")

    if action == "wakes up"
        return Wakeup(date)
    elseif action == "falls asleep"
        return Sleep(date)
    else
        regex = r"^Guard #(\d+) begins shift$"
        id :: String = match(regex, action).captures[1]
        return GuardChange(date, parse(Int, id))
    end
end

function partI()
    actions = parseLine.(readlines(open("input2.txt")))
    sort!(actions, by = x -> x.date)

    sleepMinutes = Dict{Int, Minute}()

    asleep_since = missing
    current = missing
    for action in actions
        println("Handling action: $action")
        if action isa GuardChange
            current = action.id
        elseif action isa Sleep
            @assert current !== missing
            asleep_since = action.date
        else
            @assert action isa Wakeup
            @assert asleep_since !== missing
            @assert current !== missing
            duration = convert(Dates.Minute, action.date - asleep_since)
            existing = get(sleepMinutes, current, Dates.Minute(0))
            sleepMinutes[current] = existing + duration
        end
    end

    m = maximum(values(sleepMinutes))
    println("Most sleep minutes are $m")
    mi = findmax(sleepMinutes)
    println("Most sleep other is $mi")
    md = collect(keys(sleepMinutes))[argmax(collect(values(sleepMinutes)))]
    println("Most sleep other is $md")

end