defmodule Day1 do
  def permutations([]), do: [[]]
  def permutations(list), do: for elem <- list, rest <- permutations(list--[elem]), do: [elem|rest]
  
  def numbers do
    File.cwd!() <> "/2020/inputs/day1.txt"
    |> File.stream!()
    |> Enum.map(&String.trim_trailing/1)
    |> Enum.map(&String.to_integer/1)
  end
  
  def combinations(one, two) do
    Enum.flat_map(one, fn e ->
      Enum.map(two, fn ee -> {e, ee} end)
    end)
  end

  def combinations(one, two, three) do
    Enum.flat_map(one, fn e ->
      Enum.flat_map(two, fn ee -> 
        Enum.map(three, fn eee -> {e, ee, eee} end)
      end)
    end)
  end
  
  def solveOne() do
    values = numbers()
    combinations(values, values)
    |> Enum.find(fn {e, ee} -> e + ee == 2020 and e != ee end)
    |> (fn {e, ee} -> e * ee end).()
    |> IO.inspect()
  end

  def solveTwo() do
    values = numbers()
    combinations(values, values, values)
    |> Enum.find(fn {e, ee, eee} -> e + ee + eee == 2020 and  e != ee and ee != eee end)
    |> (fn {e, ee, eee} -> e * ee * eee end).()
    |> IO.inspect()
  end
end

#Day1.solveOne()
Day1.solveTwoAlt()