defmodule Day2 do
  
  defmodule PasswordPolicy do
    defstruct ~w(min max letter)a
    
    def valid?(%PasswordPolicy{min: min, max: max, letter: l}, password) do
      password
      |> String.graphemes()
      |> Enum.count(& &1 == l)
      |> (fn count -> count >= min and count <= max end).()
    end
  end
  
  defmodule Entry do
    defstruct ~w(policy password)a
    
    def old_valid?(%Entry{policy: po, password: pa}) do
      PasswordPolicy.valid?(po, pa)
    end
    
    def new_valid?(%Entry{policy: po, password: pa}) do
      first = String.at(pa, po.min - 1)
      second = String.at(pa, po.max - 1)
      (first == po.letter and second != po.letter) or (first != po.letter and second == po.letter)
    end
  end
  
  def read do
    File.cwd!() <> "/2020/inputs/day2.txt"
    |> File.stream!()
    |> Enum.map(fn a ->
        [min, max, letter, password] = Regex.run(~r/^(\d+)-(\d+) ([[:alpha:]]): ([[:alpha:]]+)$/, a, capture: :all_but_first)
        %Entry{policy: %PasswordPolicy{min: String.to_integer(min), max: String.to_integer(max), letter: letter}, password: password}
    end)
  end
  
  def part1 do
    read()
    |> Enum.filter(&Entry.old_valid?/1)
    |> Enum.count()
    |> IO.inspect()
  end

  def part2 do
    read()
    |> Enum.filter(&Entry.new_valid?/1)
    |> Enum.count()
    |> IO.inspect()
  end


end

Day2.part1()
Day2.part2()