# Proiect OOP
Anul 2, Semestrul 1 
Facultatea "Automatica si calculatoare" CTI
Univeristatea "Politehnica" Bucuresti

Vaideanu Renata - Georgia
322 CD

- input package -> is mainly used for implementing the parameters received in the input, making
sure that everything is placed in the right structure.
    user package -> creates the fields necessary for all the parameters that can be received
        when getting the user from input.
    filter package -> creates the fields necessary for all the parameters that can be received
        when getting the filters from input. It will be used for the case filter.
    Const class -> is used for implementing the constants used in the code, so there won't
        be any magic numbers
    Input class -> contains 3 array lists: users, movies and actions.
    MovieInput class -> creates the fields necessary for all the parameters that can be received
        when getting a movie from input.
    ActionInput class -> creates the fields necessary for all the parameters that can be received
        when getting the actions from input.

- pages package -> is used for implementing the pages and the actions that can be done on every one
of them.
    Page class -> is the main page template that is going to be used for all the pages to extend.
    Login class -> used for the login on page and change page actions. Both of them will be
        extended from the page class.
    Register class -> used for the register on page and change page actions. Both of them will be
        extended from the page class.
    Movies class -> used for the movies on page and change page actions. The class will extend
        the page class. The methods in this class will be the ones for changing and on page
        (search, filte, purchase, like, rate, watch).
    SeeDetails class -> used for the change page -> see details action.
    Upgrades class -> used for the upgrades actions chose by the user.

- workflow package -> is used for the workflow itself. It is devided in three packages:
    Actions class -> used to go through all the actions that
    Errors class -> used for testing if there are any errors that may prevent the user from
        changing the page or from applying a feature.
    OutPrint -> used for desplaying the output for the neccessary cases.

The rating of a movie will be calculated like this: adding all the ratings and deviding it by
the number of times it was rated, only when a movie is printed.
