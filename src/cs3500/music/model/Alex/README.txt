Alex Aubuchon
3/4/2016
HW05

Interfaces:
Composition - general interface for a collection of notes, specifies the printed state of the model
              and the length of the model
IChromaticModel - Interface for songs using the chromatic scale of notes specifies the operations
                  needed to satisfy what was said in the homework, extends Composition

Classes:
ChromaticModel - implements IChromaticModel and is the major class of this assignment
ChromaticModelCreator - allows production of an IChromaticModel using its create method
                        (use ChromaticModelCreator.create(ChromaticModelCreator.TYPE.CHROMATIC);
                        to produce a ChromaticModel)
Note - a class for the notes used in ChromaticModel, notably immutable due to its final parameters,
       also implements comparable