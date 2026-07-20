# Chicken Invaders

## Student Information

**Name:** Melina Masoumi

**ID:** 40412035


**GitHub Repository:** https://github.com/mirrorknows/ChickenInvaders_AP

### Features

* Login and Registration
* Main Menu
* 8 Game Levels
* Normal, Fast, Zigzag, and Shooter Chickens
* Boss Battles
* Power-Ups
* Explosion Effects
* Background Music and Sound Effects
* Sound Settings
* High Scores
* Game History
* How to Play Menu
* Plane Store

## Requirements

* Java JDK
* IntelliJ IDEA
* Project image and sound resources

## How to Run

1. Open the project in IntelliJ IDEA.
2. Run the `Main.java` file.

## Game Controls

| Key                     | Action                   |
| ----------------------- | ------------------------ |
| Arrow Keys / W, A, S, D | Move the plane           |
| Space                   | Shoot                    |
| P                       | Pause or resume the game |
| Esc                     | Return to the main menu  |

## Database

The project uses an SQLite database.

**Database file:** `game.db`

data base tables:

### users

Stores user account information, game progress, sound settings, high score, and selected plane.

Main columns:

* `id`
* `username`
* `password`
* `high_score`
* `last_level`
* `music_on`
* `shot_sound_on`
* `crash_sound_on`
* `gameover_sound_on`
* `selected_plane`

### game_history

Stores the result of every completed or exited game.

Main columns:

* `id`
* `user_id`
* `score`
* `level`
* `played_time`
* `music_on`
* `shot_sound_on`
* `crash_sound_on`
* `gameover_sound_on`
