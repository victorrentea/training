SetTitleMatchMode, 2
DetectHiddenWindows, On
SetKeyDelay, 25, 25
AutoTrim, Off

lastEditorNN := "none"

;ControlGetPos, TargetX, TargetY, Width, Height, %1%, Eclipse
;MsgBox, 0, Control List: %AppWinTitle%, %TargetX% . %TargetY%

n := 3
delay := 300

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
EditorNN := lastEditorNN
ControlGet, OutputVar, Visible,,%EditorNN%, Eclipse
notFound := ErrorLevel
foundCount := 0
if (false) 
{
	;MsgBox, 0, Control List: %AppWinTitle%, The Control ClassNN is gone: %lastEditorNN%. Searching for control at %1%:%2%
	WinGet, ClassNNList, ControlList, Eclipse
	
	Loop, PARSE, ClassNNList, `n
	{
	    ControlGetPos, X, Y, Width, Height, %A_LoopField%, Eclipse
	    ;ControlGet, IsVisible, Visible,,%A_LoopField%, Eclipse
	    
	    if (X == A_Args[1] and Y == A_Args[2] and foundCount = 0)
	    {
	    	EditorNN := A_LoopField	    	
	    	foundCount ++
	    }
	}
	;MsgBox, 0, Control List: %AppWinTitle%, Control renamed from %lastEditorNN% to %EditorNN%:  count=%foundCount%
	lastEditorNN := EditorNN
}

v := A_Args[n]
;ControlSend,ahk_parent,%v%, Eclipse
ControlSend,,%v%, ahk_id %1%
n := n + 1
if (n >A_Args.length())
	ExitApp
return
