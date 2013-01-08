# canvas-frame

A Clojure library designed to ... well, a place to play with clojure
mostly for myself.  Right now (1/8/2013) the project is runnable
thanks to [Leiningen][Leiningen], but it doesn't do anythnig but 
generate a single java swing frame and display a mathematically
generated image.

## Usage

You can see the progress by running:

```
%> lein run start
```

`start` here is just a command / keyword used to pick the image.  This
functionality isn't really anything more than stubbed out at the moment.
The hope is that eventually a number of different images can be generated
by picking the 'name' (or something -- haven't really decided on
implementation).

## Generated Images

(`lein run start`)

![ScreenShot] (https://raw.github.com/lcaballero/canvas-frame/master/images/diamonds.png)

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.

[Leiningen]: https://github.com/technomancy/leiningen "Source Location for Leiningen - Clojure version of Ruby Gem and Bundler"

