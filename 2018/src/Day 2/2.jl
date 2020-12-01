#=
2:
- Julia version: 1.0.2
- Author: Niko
- Date: 2018-12-02
=#

using StatsBase

function partI()
    two_counter = 0
    three_counter = 0

    for id in readlines(open("input.txt"))
        characters = split(id, "")
        counter = values(countmap(characters))
        if 2 in counter
            two_counter += 1
        end
        if 3 in counter
            three_counter += 1
        end
    end

    checksum = two_counter * three_counter
    print("Checksum is $checksum\n")
end

using StringDistances

function partII()
    ids = readlines(open("input.txt"))
    letters = []

    for id_x in ids
        for id_y in ids
            # Check if the two strings only differ in one character
            distance = evaluate(Hamming(), id_x, id_y)
            if distance == 1
                same = []
                for (x, y) in zip(id_x, id_y)
                    if x == y
                        append!(same, x)
                    end
                end
                string = join(same, "")
                print("Is   $string\n")
                print("X is $id_x\n")
                print("Y is $id_y\n")
                return
            end
        end
    end
end