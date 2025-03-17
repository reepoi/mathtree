#let TODO(note) = text(note, fill: aqua)
#let TimeStepIndex = $j$
#let SystemStateName = math.attach.with($bold(v)$, b: TimeStepIndex)
#let Name = (
    System: (
        State: math.attach.with($bold(v)$, b: TimeStepIndex),
    ),
)
#let System = (
    State: (
        Pred: Name.System.State,
        True: (Name.System.State).with(t: $dagger$),
    ),
)

= Data assimilation
arst

$#(System.State.Pred)()_dagger$

#(System.State.True)()
