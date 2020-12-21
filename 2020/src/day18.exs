defmodule Day18 do
  @behaviour Runner.Day

  def read(file) do
    File.stream!(file)
   |> Stream.map(&String.trim/1)
  end
  
  def run(expression, replacer, restorer) do
    replacer.(expression)
    |> Code.string_to_quoted!()
    |> restorer.()
    |> Code.eval_quoted()
    |> elem(0)
  end
  
  defp replacer1(line) do
    String.replace(line, "*", "-")
  end
  
  defp restorer1({:-, ops, params}) do
    new_params = Enum.map(params, &restorer1/1)
    {:*, ops, new_params}
  end
  defp restorer1({op, ops, params}) do
    new_params = Enum.map(params, &restorer1/1)
    {op, ops, new_params}
  end
  defp restorer1(x), do: x

  @impl true
  def part1(file) do
    read(file)
    |> Stream.map(fn line -> run(line, &replacer1/1, &restorer1/1) end)
    |> Enum.sum()
    |> IO.inspect()
  end

  defp replacer2(line) do
    String.replace(line, "*", "--")
  end

  defp restorer2({:--, ops, params}) do
    new_params = Enum.map(params, &restorer2/1)
    {:*, ops, new_params}
  end
  defp restorer2({op, ops, params}) do
    new_params = Enum.map(params, &restorer2/1)
    {op, ops, new_params}
  end
  defp restorer2(x), do: x

  @impl true
  def part2(file) do
    read(file)
    |> Stream.map(fn line -> run(line, &replacer2/1, &restorer2/1) end)
    |> Enum.sum()
    |> IO.inspect()
  end
end
