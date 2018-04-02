SetTitleMatchMode, 2
DetectHiddenWindows, On
ControlGetFocus, SelectedControlClassNN, Eclipse
ControlGetPos, TargetX, TargetY, Width, Height, %SelectedControlClassNN%, Eclipse
ControlGet, ControlHWND, Hwnd, ,%SelectedControlClassNN%, Eclipse
WinGetTitle, EclipseTitle, Eclipse
FileAppend,%SelectedControlClassNN% %TargetX% %TargetY% %ControlHWND% || %EclipseTitle%,*
return