defmodule Day20 do
  @behaviour Runner.Day

  defmodule Tile do
    defstruct [:data, :id]

    def all_orientations(%Tile{}= tile) do
      
    end
    
    defp flip_vertical(%Tile{data: data} = t) do
      new_data = Enum.map(data, fn {{x, y}, v} ->  end)
    end

  end
  
  def read(file) do
    File.read!(file)
    |> String.split("\n\n", trim: true)
    |> Enum.map(fn lines -> parse_tile(lines) end)
  end
  
  defp parse_tile(tile) do
    [identifier | data] = String.split(tile, ":", trim: true)
    %Tile{
      id: parse_identifier(identifier),
      data: Enum.map(data, &parse_grid/1)
    }
  end
  
  defp parse_identifier(line) do
    [id] = Regex.run(~r/Tile (\d+)/, line, capture: :all_but_first)
    String.to_integer(id)
  end
  
  defp parse_grid(lines) do
    String.split(lines, "\n")
    |> Enum.with_index()
    |> Enum.flat_map(fn {line, x} ->
        String.graphemes(line)
        |> Enum.with_index()
        |> Enum.map(fn {token, y} -> {{x, y}, token} end)
      end)
    |> Map.new()
  end

  @impl true
  def part1(file) do
    read(file)
    |> Enum.count()
    |> IO.inspect()
  end

  @impl true
  def part2(file) do
    
  end
end

Day20.part1("2020/src/inputs/day20.txt")