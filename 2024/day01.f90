program day01
    implicit none

    integer :: lines
    integer, allocatable :: left_list(:), right_list(:)

    call read_data(lines, left_list, right_list)

    call part1(lines, left_list, right_list)
    call part2(lines, left_list, right_list)

contains
    ! Fortran has no sorting built in
    ! Modified from https://www.mjr19.org.uk/IT/sorts/sorts.f90
    recursive subroutine quicksort(array)
        integer, intent(inout) :: array(:)
        integer :: temp, pivot, i, j, last, left, right

        last = size(array)

        if (last<50) then ! use insertion sort on small arrays
            do i = 2, last
                temp = array(i)
                do j = i - 1, 1, -1
                    if (array(j)<=temp) then
                        exit
                    end if
                    array(j + 1) = array(j)
                enddo
                array(j + 1) = temp
            enddo
            return
        endif
        ! find median of three pivot
        ! and place sentinels at first and last elements
        temp = array(last / 2)
        array(last / 2) = array(2)
        if (temp>array(last)) then
            array(2) = array(last)
            array(last) = temp
        else
            array(2) = temp
        endif
        if (array(1)>array(last)) then
            temp = array(1)
            array(1) = array(last)
            array(last) = temp
        endif
        if (array(1)>array(2)) then
            temp = array(1)
            array(1) = array(2)
            array(2) = temp
        endif
        pivot = array(2)

        left = 3
        right = last - 1
        do
            do while(array(left)<pivot)
                left = left + 1
            enddo
            do while(array(right)>pivot)
                right = right - 1
            enddo
            if (left>=right) then
                exit
            end if
            temp = array(left)
            array(left) = array(right)
            array(right) = temp
            left = left + 1
            right = right - 1
        enddo
        if (left==right) then
            left = left + 1
        end if
        call quicksort(array(1:left - 1))
        call quicksort(array(left:))

    end subroutine quicksort

    subroutine read_data(lines, left_list, right_list)
        integer, intent(out) :: lines
        integer, allocatable, intent(out) :: left_list(:), right_list(:)

        integer :: line_number, status, input_data

        open(newunit = input_data, file = "day01.in", status = "old", action = "read")

        ! Count the lines in the file
        lines = 0
        do
            read(input_data, *, iostat = status)
            if (status /= 0) then
                exit ! Stop the loop, since we are at the end of the file.
            end if
            lines = lines + 1
        end do

        rewind(input_data)

        allocate(left_list(lines), right_list(lines))

        ! Read the numbers into an array
        do line_number = 1, lines
            ! This is "List-directed I/O"
            ! https://docs.oracle.com/cd/E19957-01/805-4939/6j4m0vnc5/index.htmlc
            ! https://www.intel.com/content/www/us/en/docs/fortran-compiler/developer-guide-reference/2023-1/rules-for-list-directed-sequential-read-statements.html#GUID-AA687878-D0C4-4470-928D-7A5E4D19A62E
            read(input_data, *) left_list(line_number), right_list(line_number)
        end do
        close(input_data)

        call quicksort(left_list)
        call quicksort(right_list)
    end subroutine read_data

    subroutine part1(lines, left_list, right_list)
        integer, intent(in) :: lines
        integer, intent(in) :: left_list(:), right_list(:)

        integer, allocatable :: differences(:)

        allocate(differences(lines))

        ! Working with arrays is nice in fortran
        differences = abs(left_list - right_list)

        print *, "Part 1:", sum(differences)

    end subroutine part1

    subroutine part2(lines, left_list, right_list)
        integer, intent(in) :: lines
        integer, intent(in) :: left_list(:), right_list(:)

        integer :: line_number
        integer, allocatable :: results(:)

        allocate(results(lines))

        do line_number = 1, lines
            results(line_number) = left_list(line_number) * count(right_list==left_list(line_number))
        end do

        print *, "Part 2:", sum(results)

    end subroutine part2
end program
