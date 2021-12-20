defmodule Day5 do
  @behaviour Runner.Day

  defmodule BoardingPass do
    defstruct ~w(rows columns)a

    def parse(line) do
      chars = String.trim(line) |> String.graphemes()

      %BoardingPass{
        rows: Enum.slice(chars, 0..6),
        columns: Enum.slice(chars, 7..-1)
      }
    end
  end

  def read(file) do
    File.stream!(file)
    |> Enum.map(&BoardingPass.parse/1)
  end

  defp do_step(s, min..max) when s in ~w(F L) do
    min..(min + div(max - min, 2))
  end

  defp do_step(s, min..max) when s in ~w(B R) do
    (min + div(max - min, 2) + 1)..max
  end

  defp calculate_seat_id(%BoardingPass{rows: rows, columns: columns}) do
    row..row = Enum.reduce(rows, 0..127, &do_step/2)
    col..col = Enum.reduce(columns, 0..7, &do_step/2)
    row * 8 + col
  end

  defp get_seat_ids(file) do
    read(file)
    |> Enum.map(&calculate_seat_id/1)
  end

  @impl true
  def part1(file) do
    get_seat_ids(file)
    |> Enum.max()
    |> IO.inspect()
  end

  @impl true
  def part2(file) do
    found_ids = get_seat_ids(file) |> Enum.sort()
    # We skip the first n seats, since they don't exist.
    Enum.zip(Enum.min(found_ids)..(126 * 8), found_ids)
    |> Enum.find(fn {i, id} -> i != id end)
    |> elem(0)
    |> IO.inspect()
  end
end
