program day02
    implicit none

    call part1
    call part2
contains

    subroutine calculate_difference(array, diff)
        integer, dimension(:), intent(in) :: array
        integer, dimension(:), intent(out) :: diff

        integer :: level

        do level = 1, size(array) - 1
            diff(level) = -(array(level) - array(level + 1))
        end do

    end subroutine calculate_difference

    function is_increasing(array) result(conforms)
        integer, dimension(:), intent(in) :: array
        logical :: conforms

        conforms = all(array == 1 .or. array == 2 .or. array == 3)
    end function is_increasing

    function is_decreasing(array) result(conforms)
        integer, dimension(:), intent(in) :: array
        logical :: conforms

        conforms = all(array == -1 .or. array == -2 .or. array == -3)
    end function is_decreasing

    subroutine read_data(data)
        use io

        character(len = :), allocatable, intent(out), dimension(:) :: data
        integer :: line_number, input_data, lines

        ! Temporary buffer for reading.

        lines = count_lines("day02.in")
        allocate(character(len = 50) :: data(lines))

        open(newunit = input_data, file = "day02.in", status = "old", action = "read")
        ! We cannot use list-directed I/O here, since we do not know how many numbers there will be per line.
        do line_number = 1, lines
            read(input_data, '(A)') data(line_number)
        end do
        close(input_data)
    end subroutine read_data

    subroutine part1
        use fpm_strings

        character(len = :), allocatable, dimension(:) :: data, char_levels
        integer, allocatable, dimension(:) :: differences, levels
        integer :: report_number, safe_reports

        call read_data(data)

        safe_reports = 0

        do report_number = 1, size(data)
            call split(data(report_number), char_levels, " ")

            allocate(levels(size(char_levels)))
            read(data(report_number), *) levels

            allocate(differences(size(levels) - 1))
            call calculate_difference(levels, differences)

            if (is_increasing(differences) .or. is_decreasing(differences)) then
                safe_reports = safe_reports + 1
            end if

            deallocate(levels)
            deallocate(differences)
        end do

        print *, "Part 1:", safe_reports
    end subroutine part1

    subroutine part2
        use fpm_strings

        character(len = :), allocatable, dimension(:) :: data, char_levels
        integer, allocatable, dimension(:) :: differences, levels, levels_tolerated, differences_tolerated
        integer :: report_number, safe_reports, removing

        call read_data(data)

        safe_reports = 0

        do report_number = 1, size(data)
            call split(data(report_number), char_levels, " ")

            allocate(levels(size(char_levels)))
            read(data(report_number), *) levels

            allocate(differences(size(levels) - 1))
            call calculate_difference(levels, differences)

            if (is_increasing(differences) .or. is_decreasing(differences)) then
                safe_reports = safe_reports + 1
                deallocate(levels, differences)
                cycle
            end if

            ! Brute force all possibilities.
            allocate(levels_tolerated(size(levels) - 1))
            allocate(differences_tolerated(size(differences) - 1))

            do removing = 1, size(levels)
                levels_tolerated = [levels(1:removing - 1), levels(removing + 1:size(levels))]
                call calculate_difference(levels_tolerated, differences_tolerated)

                if (is_decreasing(differences_tolerated) .or. is_increasing(differences_tolerated)) then
                    safe_reports = safe_reports + 1
                    exit
                end if
            end do

            deallocate(levels, differences, levels_tolerated, differences_tolerated)
        end do

        print *, "Part 2:", safe_reports
    end subroutine part2
end program
