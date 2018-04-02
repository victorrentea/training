SetTitleMatchMode, 2
DetectHiddenWindows, On
SetKeyDelay, 25, 25
AutoTrim, Off

n := 3

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
v := A_Args[n]
if (v != "NOOP")
	ControlSend,,%v%, ahk_id %1%
n := n + 1
if (n >A_Args.length())
	ExitApp
return
