defmodule Day8 do
  @behaviour Runner.Day

  def read(file) do
    File.stream!(file)
    |> Enum.map(&String.trim/1)
    |> Enum.map(fn s ->
      [operation, number] = String.split(s, " ")
      {operation, String.to_integer(number)}
    end)
  end

  defp execute("nop", _), do: {1, 0}
  defp execute("acc", value), do: {1, value}
  defp execute("jmp", value), do: {value, 0}

  defp run_program(ops) do
    Stream.unfold({0, 0, MapSet.new()}, fn {next_op, acc, executed} ->
      cond do
        is_tuple(acc) ->
          case acc do
            {:loop, _} -> nil
            {:halt, _} -> nil
          end

        MapSet.member?(executed, next_op) ->
          {{:loop, acc}, {next_op, {:loop, acc}, executed}}

        next_op >= length(ops) ->
          {{:halt, acc}, {next_op, {:halt, acc}, executed}}

        true ->
          {command, value} = Enum.at(ops, next_op)
          {d_next_op, d_acc} = execute(command, value)
          {acc, {next_op + d_next_op, acc + d_acc, MapSet.put(executed, next_op)}}
      end
    end)
    |> Enum.take(-1)
  end

  @impl true
  def part1(file) do
    read(file)
    |> run_program()
    |> IO.inspect()
  end

  defp replace_and_run(ops, at, new, val) do
    new_ops = List.replace_at(ops, at, {new, val})

    case run_program(new_ops) do
      [halt: value] -> {:halt, value}
      _ -> {:cont, at + 1}
    end
  end

  @impl true
  def part2(file) do
    ops = read(file)
    # For each operator, replace at index i if possible and check.
    Enum.reduce_while(ops, 0, fn op, acc ->
      case op do
        {"acc", _} -> {:cont, acc + 1}
        {"jmp", val} -> replace_and_run(ops, acc, "nop", val)
        {"nop", val} -> replace_and_run(ops, acc, "jmp", val)
      end
    end)
    |> IO.inspect()
  end
end
