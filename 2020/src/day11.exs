defmodule Day11 do
  @behaviour Runner.Day
  
  def read(file) do
    File.stream!(file)
    |> Enum.map(&String.trim/1)
    |> Enum.with_index()
    |> Enum.flat_map(fn {row, i} -> String.graphemes(row) |> Enum.with_index() |> Enum.map(fn {e, j} -> {{i, j}, e} end) end)
    |> Map.new()
  end
  
  defp neighbours(grid, {x, y}) do
    [
      Map.get(grid, {x - 1, y - 1}),
      Map.get(grid, {x - 1, y}),
      Map.get(grid, {x - 1, y + 1}),
      Map.get(grid, {x    , y - 1}),
      Map.get(grid, {x    , y + 1}),
      Map.get(grid, {x + 1, y - 1}),
      Map.get(grid, {x + 1, y}),
      Map.get(grid, {x + 1, y + 1}),
    ]
    |> Enum.filter(fn e -> not is_nil(e) end)
  end
  
  defp find_next(grid, {x, y}, delta_x, delta_y) do
    Stream.unfold({x + delta_x, y + delta_y}, fn {x, y} = p ->
      value = Map.get(grid, p)
      if is_nil(value), do: nil, else: {value, {x + delta_x, y + delta_y}}
    end)
    |> Enum.to_list()
    |> Enum.find(fn f -> f != "." end)
  end
  
  defp neighbours2(grid, p) do
    [
      find_next(grid, p, -1, -1),
      find_next(grid, p, -1, 0),
      find_next(grid, p, -1, 1),
      find_next(grid, p, 0, -1),
      find_next(grid, p, 0, 1),
      find_next(grid, p, 1, -1),
      find_next(grid, p, 1, 0),
      find_next(grid, p, 1, 1),
    ]
    |> Enum.filter(fn e -> not is_nil(e) end)
  end
  
  defp print(grid) do
    Enum.to_list(grid)
    |> Enum.sort()
    |> Enum.reduce(-1, fn {{x, _}, v}, a ->
      if x > a do
        IO.write("\n")
      end
      IO.write(v)
      x
    end)
    IO.write("\n")
    grid
  end
  
  defp do_step(_, _, ".", _, _), do: "."
  defp do_step(grid, p, "L", near, _) do
    a = near.(grid, p)
    |> Enum.all?(fn n -> n in [".", "L"] end)
    if a, do: "#", else: "L"
  end
  defp do_step(grid, p, "#", near, t) do
    aa = near.(grid, p)
    
    a = Enum.count(aa, fn n -> n == "#" end)
    if a >= t, do: "L", else: "#"
  end
  
  def step(grid, n, tolerate \\ 4) do
    Map.new(grid, fn {key, value} -> {key, do_step(grid, key, value, n, tolerate)} end)
  end

  @impl true
  def part1(file) do
    grid = read(file)
    
    Stream.unfold({1, grid}, fn {a, grid} ->
      new_grid = step(grid, &neighbours/2)
      if new_grid != grid do
        {new_grid, {a + 1, new_grid}}
      else
        nil
      end
    end)
    |> Enum.to_list()
    |> Enum.at(-1)
    |> Enum.count(fn {_, v} -> v == "#" end)
    |> IO.inspect()
  end
  
  
  @impl true
  def part2(file) do
    grid = read(file)

    Stream.unfold({1, grid}, fn {a, grid} ->
      new_grid = step(grid, &neighbours2/2, 5)
      if new_grid != grid do
        {new_grid, {a + 1, new_grid}}
      else
        nil
      end
    end)
    |> Enum.to_list()
    |> Enum.at(-1)
      #|> print()
    |> Enum.count(fn {_, v} -> v == "#" end)
    |> IO.inspect()
  end
end

Day11.part2("2020/src/inputs/day11.txt")