defmodule Day14 do
  @behaviour Runner.Day
  use Bitwise
  
  def read(file) do
   File.stream!(file)
   |> Enum.map(fn 
     "mask" <> rest -> 
       mask = rest |> String.trim() |> String.split("= ") |> Enum.at(1)
       # M1: all 1's should be ignored
       # M0: all 0's should be ignored
       ignore = String.replace(mask, "1", "0") |> String.replace("X", "1") |> Integer.parse(2) |> elem(0)
       set = String.replace(mask, "X", "0") |> Integer.parse(2) |> elem(0)
       {:mask, ignore, set, mask}
     "mem"  <> rest ->
      [pos, number] = Regex.run(~r/\[(\d+)\] = (\d+)/, rest, capture: :all_but_first)
      {:memory, pos, String.to_integer(number)}
    end)
  end
  
  defmodule State do
    defstruct [ignore: 0, set: 0, raw: "", memory: %{}]
    
    def run1(state, {:mask, i, s, w}), do: %{state | ignore: i, set: s, raw: w}
    
    def run1(%State{ignore: ignore, set: set, memory: m} = state, {:memory, location, number}) do
      # To apply the bitmask to a number, we do two passes:
      # - First, set every position to 1 where required
      # - Secondly, set every position to 0 where required
      result = set ||| (number &&& ignore)
      %{state | memory: Map.put(m, location, result)}
    end
    
    def set_all(memory, [], [], address, value) do
      Map.put(memory, Integer.undigits(address, 2), value)
    end
    
    def set_all(memory, ["X" | mask_rest], [_ | key_rest], address, value) do
      memory
      |> set_all(mask_rest, key_rest, address ++ [0], value)
      |> set_all(mask_rest, key_rest, address ++ [1], value)
    end

    def set_all(memory, ["1" | mask_rest], [_ | key_rest], address, value) do
      set_all(memory, mask_rest, key_rest, address ++ [1], value)
    end

    def set_all(memory, ["0" | mask_rest], [bit | key_rest], address, value) do
      set_all(memory, mask_rest, key_rest, address ++ [bit], value)
    end
    
    def run2(%State{raw: w, memory: m} = state, {:memory, location, number}) do
      address = String.to_integer(location) |> Integer.digits(2)
      address = List.duplicate(0, 36 - length(address)) ++ address
      mask = String.graphemes(w)
      memory = set_all(m, mask, address, [], number)
      %{state | memory: memory}
    end

    defdelegate run2(s, i), to: State, as: :run1
  end

  @impl true
  def part1(file) do
    read(file)
    |> Enum.reduce(%State{}, fn x, state -> State.run1(state, x) end)
    |> (fn %{memory: m} -> m end).()
    |> Enum.map(fn {_, v} -> v end)
    |> Enum.sum()
    |> IO.inspect()
  end

  @impl true
  def part2(file) do
    read(file)
    |> Enum.reduce(%State{}, fn x, state -> State.run2(state, x) end)
    |> (fn %{memory: m} -> m end).()
    |> Enum.map(fn {_, v} -> v end)
    |> Enum.sum()
    |> IO.inspect()
  end
end

Day14.part2("2020/src/inputs/day14.txt")
