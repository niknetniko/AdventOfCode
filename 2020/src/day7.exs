defmodule Day7 do
  @behaviour Runner.Day
  
  def read(file) do
    File.stream!(file)
    |> Map.new(&parse_line/1)
  end
  
  defp parse_colour(colour) do
    [colour, _] = Regex.run(~r/(.*) (bag|bags)/, colour, capture: :all_but_first)
    colour
  end
  
  defp parse_line(line) do
    [key, colours] = String.trim(line) |> String.split(" contain ")
    key = parse_colour(key)
    if colours == "no other bags." do
      {key, []}
    else
      colours = String.split(colours, ", ")
                |> Enum.map(fn colour ->
        [amount, colour] = Regex.run(~r/^(\d+) (.*)$/, colour, capture: :all_but_first)
        %{amount: String.to_integer(amount), colour: parse_colour(colour)}
      end)
      {key, colours}
    end
    
  end
  
  defp contain?(map, color, search) do
    containing = Map.get(map, color) |> Enum.map(fn %{colour: colour} -> colour end)
    if search in containing do
      true
    else
      Enum.any?(containing, fn c -> contain?(map, c, search) end)
    end
  end
  
  defp do_count(map, color) do
    Map.get(map, color)
    |> Enum.map(fn %{colour: c, amount: a} -> a * (do_count(map, c) + 1) end)
    |> Enum.sum()
  end
  
  @impl true
  def part1(file) do
    map = read(file)
    Enum.count(map, fn {k, _} -> contain?(map, k, "shiny gold") end)
    |> IO.inspect()
  end

  @impl true
  def part2(file) do
    read(file)
    |> do_count("shiny gold")
    |> IO.inspect()
  end
end
