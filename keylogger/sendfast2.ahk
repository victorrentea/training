SetTitleMatchMode, 2
DetectHiddenWindows, On
SetKeyDelay, 15, 15

n := 1

:*:q::
:*:w::
:*:e::
:*:r::
:*:t::
:*:y::
:*:u::
:*:i::
:*:o::
:*:p::
:*:y::
:*:f::
:*:g::
:*:h::
:*:j::
:*:k::
:*:l::
:*:c::
:*:v::
:*:b::
:*:n::
:*:m::
:*:,::
:*:a::
:*:s::
:*:d::
:*:x::
:*:z::
;SendInput %n%
v := A_Args[n]
Send %v%
n := n + 1
if (n >A_Args.length())
	suspend
return
