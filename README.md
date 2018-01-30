# elo-ranking-program

**Initial setup:**
* Initial data of players and matches is shipped as a part of service.
* Reference data (rank boundaries for forecasting) is hardcoded. Boundaries have been taken from wiki page of [ELO ranking](https://en.wikipedia.org/wiki/Elo_rating_system) 

**Current possibilities:**
* Pre-loading initial data to H2 database on startup.
* REST API documentation can be accessed via '/swagger-ui.html'
* All the REST endpoints return fully qualified JSON for front-end needs
* Following methods for data manipulation supplied:
    * Retrieval of players ordered by predefined criteria
    * Retrieval of player details
    * Player creation
    * Match registration
    * Tournament forecasting
    * Health Check
    * Settings update
* System performs formatted output to console according to default settings. The last ones can be updated with following possible values:
    * output format: plain/json
    * output destination: console/file on file system
    
