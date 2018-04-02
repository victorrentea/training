SetTitleMatchMode, 2
DetectHiddenWindows, On
SetKeyDelay, 25, 25
AutoTrim, Off

lastEditorNN := "none"

;ControlGetPos, TargetX, TargetY, Width, Height, %1%, Eclipse
;MsgBox, 0, Control List: %AppWinTitle%, %TargetX% . %TargetY%

n := 2

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
EditorNN := A_Args[1]
ControlGet, OutputVar, Visible,,%1%, Eclipse
notFound := ErrorLevel
if (notFound ) 
{
	MsgBox, 0, Control List: %AppWinTitle%, The Control ClassNN is gone: %1%
	WinGet, ClassNNList, ControlList, Eclipse
	
	Loop, PARSE, ClassNNList, `n
	{
	    ControlGetPos, X, Y, Width, Height, %A_LoopField%, Eclipse
	    if (Abs(X - TargetX) < 5 and Abs(Y - TargetY) < 5) 
	    {
	    	EditorNN := A_LoopField
	    }
	}
	MsgBox, 0, Control List: %AppWinTitle%, Control renamed from %1% to %EditorNN%
}

v := A_Args[n]
ControlSend,%EditorNN%,%v%%notFound%, Eclipse
n := n + 1
if (n >A_Args.length())
	ExitApp
return
