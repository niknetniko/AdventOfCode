module io
    implicit none

    public :: count_lines

contains

    !> Count the number of lines in a file.
    function count_lines(file) result(lines)
        character(len = *), intent(in) :: file
        integer :: lines
        integer :: file_io, status

        ! Opening and closing the file multiple times in not that efficient, but meh.
        open(newunit = file_io, file = file, status = "old", action = "read")

        lines = 0
        do
            read(file_io, *, iostat = status)
            if (status /= 0) then
                exit ! Stop the loop, since we are at the end of the file.
            end if
            lines = lines + 1
        end do

        close(file_io)
        return
    end function count_lines

end module io
