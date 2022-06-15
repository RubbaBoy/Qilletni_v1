## Qilletni
Generate infinite, dynamic, and intelligent Spotify queues.

Qilletni is a multi-platform application to allow for drag-and-drop block-based organizing of components to sequentially generate Spotify queues as you listen. The component based system is best compared to [MIT's Scratch](https://scratch.mit.edu/). Once you create a queue generator, upon starting it through the app, a few songs are added to your linked Spotify account's queue. As you play the queued songs, more are added, all according to the rules you set in the components.

This project stemmed from Adam's obsession of queuing up a large selection of songs, in a way that could be structured. This includes patterns like queuing up the same few songs over and over again, and wanting to continue playing from a playlist. This also introduces concepts like weighted playlists, which can play songs more frequently in playlists or existing queues.

Available components include:
- Function blocks
- For loop (with iteration options) to iterate through nested components
- Single preset song player
- Spotify collection playing (including playlist, artist, album and on-the-fly searching)
- Last.fm playing (including top tracks, top artists, top albums, and loved tracks)
- Weighted song playing

### Development

Development at the moment is done in an agile-like format through [GitHub Projects](https://github.com/users/RubbaBoy/projects/3) by [Adam Yarris](https://github.com/RubbaBoy) and [Max Burdett](https://github.com/lollygagger). Sprints are 2 weeks long, with each user story being converted into an issue when progress on it begins. Each story is developed on a separate branch in the format of `QTNB-#-issue-description` where `#` is the issue number, and `issue-description` is a concise description of the story. The ID `QTNB` comes from "Qilletni Backend", in the future the frontend will be `QTNF`.

#### Setting up a dev environment

An `.env` file must be created in the root directory, in the format of:
```
SPOTIFY_CLIENT_ID=***
SPOTIFY_CLIENT_SECRET=***
POSTGRES_USER=user
POSTGRES_PASSWORD=pass
```

Replacing the given values with your desired ones.

To build the program, run `./build.sh` in your terminal.

From here, you should be able to run `docker-compose up` in the root directory of this project.

#### IDE Development

For development in your IDE, along with completing the above section, you must have the following environment variables set (some examples are given, replace as necessary):
```
GRPC_PORT=9090
HTTP_PORT=8000
REDIRECT_URL=http://localhost:8000/redirect
SPOTIFY_CLIENT_ID=***
SPOTIFY_CLIENT_SECRET=***
POSTGRES_USER=user
POSTGRES_PASS=pass
POSTGRES_URL=jdbc:postgresql://localhost:5432/qilletni
```

After the environment variables are set, run the `database` service from the `docker-compose.yml` file, and run the Spring Boot application as normal.
