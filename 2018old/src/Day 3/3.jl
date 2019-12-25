#=
3:
- Julia version: 1.0.2
- Author: Niko
- Date: 2018-12-03
=#

using SparseArrays

struct Cut
    id::Int
    x::Int
    y::Int
    width::Int
    height::Int
end

function parse(line::String)
    regex = r"^#(\d+) @ (\d+),(\d+): (\d+)x(\d+)$"
    id, x, y, dx, dy = Base.parse.(Int, match(regex, line).captures)
    return id, x + 1, y + 1, dx, dy
end

function read()
    cuts = parse.(readlines(open("input.txt")))
    xm = maximum(getindex.(cuts, 2) .+ getindex.(cuts, 4))
    ym = maximum(getindex.(cuts, 3) .+ getindex.(cuts, 5))

    # Plot everything in grid
    grid = zeros(Int, xm, ym)

    return cuts, grid
end

function partI()
    cuts, gird = read()

    for (_, x, y, dx, dy) in cuts
        grid[x:(x + dx - 1), y:(y + dy - 1)] .+= 1
    end

    relevant = count(x -> x > 1, grid)
    print("Total overlapping is $relevant\n")
end


function partII()
    cuts, grid = read()

    for (_, x, y, dx, dy) in cuts
        grid[x:(x + dx - 1), y:(y + dy - 1)] .+= 1
    end

    for (id, x, y, dx, dy) in cuts
        # Could do all(a .== 1), but this is slow
        if all(a -> a == 1, grid[x:(x + dx - 1), y:(y + dy - 1)])
            print("Found with id #$id\n")
            return
        end

    end
end