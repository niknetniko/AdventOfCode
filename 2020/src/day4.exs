defmodule Day4 do
  defmodule Passport do
    def parse(text) do
      text
      |> String.split()
      |> Enum.map(&String.split(&1, ":"))
      |> Enum.map(fn [name, value] -> {String.to_atom(name), value} end)
      |> Map.new()
    end

    def valid1?(passport) do
      Enum.all?(~w(byr iyr eyr hgt hcl ecl pid)a, &Map.has_key?(passport, &1))
    end
    
    def valid2?(passport) do
      Enum.all?(passport, fn {key, value} -> valid_field?(key, value) end)
    end
    
    defp between?(value, min, max) do
      case Integer.parse(value) do
        {number, _} -> number in min..max
        _ -> false
      end
    end
    
    defp valid_field?(:byr, value), do: between?(value, 1920, 2002)
    defp valid_field?(:iyr, value), do: between?(value, 2010, 2020)
    defp valid_field?(:eyr, value), do: between?(value, 2020, 2030)
    defp valid_field?(:hgt, <<part :: binary-size(3)>> <> "cm"), do: between?(part, 150, 193)
    defp valid_field?(:hgt, <<part :: binary-size(2)>> <> "in"), do: between?(part, 59, 76)
    defp valid_field?(:hgt, _), do: false
    defp valid_field?(:hcl, value), do: Regex.match?(~r/^#[0-9a-f]{6}/, value)
    defp valid_field?(:ecl, value), do: value in ~w(amb blu brn gry grn hzl oth)
    defp valid_field?(:pid, value), do: Regex.match?(~r/^\d{9}$/, value)
    defp valid_field?(:cid, _), do: true
  end
  
  def read() do
    File.cwd!() <> "/2020/src/inputs/day4.txt"
    |> File.read!()
    |> String.split("\n\n")
    |> Enum.map(&String.trim/1)
    |> Enum.map(&Passport.parse/1)
  end
  
  def part1() do
    read()
    |> Enum.filter(&Passport.valid1?/1)
    |> Enum.count()
    |> IO.inspect()
  end

  def part2() do
    read()
    |> Enum.filter(&Passport.valid1?/1)
    |> Enum.filter(&Passport.valid2?/1)
    |> Enum.count()
    |> IO.inspect()
  end
end

Day4.part1()
Day4.part2()