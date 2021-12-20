defmodule Day2 do
  @behaviour Runner.Day

  defmodule PasswordPolicy do
    defstruct ~w(min max letter)a

    def valid?(%PasswordPolicy{min: min, max: max, letter: l}, password) do
      password
      |> String.graphemes()
      |> Enum.count(&(&1 == l))
      |> (fn count -> count in min..max end).()
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

  def read(file) do
    File.stream!(file)
    |> Enum.map(fn a ->
      [min, max, letter, password] =
        Regex.run(~r/^(\d+)-(\d+) ([[:alpha:]]): ([[:alpha:]]+)$/, a, capture: :all_but_first)

      %Entry{
        policy: %PasswordPolicy{
          min: String.to_integer(min),
          max: String.to_integer(max),
          letter: letter
        },
        password: password
      }
    end)
  end

  @impl true
  def part1(file) do
    read(file)
    |> Enum.count(&Entry.old_valid?/1)
    |> IO.inspect()
  end

  @impl true
  def part2(file) do
    read(file)
    |> Enum.count(&Entry.new_valid?/1)
    |> IO.inspect()
  end
end
