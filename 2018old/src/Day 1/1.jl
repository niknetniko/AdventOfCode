#=
1:
- Julia version: 1.0.2
- Author: Niko
- Date: 2018-12-01
=#

# Part I
partI() = sum(parse.(Int, readlines(open("input.txt"))))

# Part II
using Base.Iterators

function partII()
    changes = cycle(parse.(Int, readlines(open("input.txt"))))
    current = 0
    occured = Dict{Int, Bool}()
    for change in changes
        #print("Adding $change to $current\n")
        current += change
        existing = get(occured, current, false)
        if existing
            print(current)
            break
        end
        occured[current] = true
    end
end