defmodule Day15 do
  @behaviour Runner.Day

  def read(file) do
    File.read!(file)
    |> String.trim()
    |> String.split(",")
    |> Enum.map(&String.to_integer/1)
  end

  defmodule State do
    defstruct turn: 0, ages: %{}, last: 0

    def initialise(initial_numbers) do
      %State{
        turn: length(initial_numbers),
        ages: Enum.slice(initial_numbers, 0..-2) |> Enum.with_index(1) |> Map.new(),
        last: Enum.at(initial_numbers, -1)
      }
    end

    def do_turn(%State{turn: turn, ages: ages, last: last}) do
      last_said = Map.get(ages, last, turn)
      age = turn - last_said
      # IO.inspect("At turn #{turn}, last is #{last}, age is #{age} from #{inspect(ages)}")
      ages = Map.put(ages, last, turn)
      %State{turn: turn + 1, ages: ages, last: age}
    end
  end

  defp find_nth_number(initials, n) do
    initial = State.initialise(initials)

    (initial.turn + 1)..n
    |> Enum.reduce(initial, fn _, a -> State.do_turn(a) end)
    |> (fn %State{last: l} -> l end).()
  end

  @impl true
  def part1(file) do
    read(file)
    |> find_nth_number(2020)
    |> IO.inspect()
  end

  @impl true
  def part2(file) do
    read(file)
    |> find_nth_number(30_000_000)
    |> IO.inspect()
  end
end
