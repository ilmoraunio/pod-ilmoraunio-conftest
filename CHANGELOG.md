# Changelog

This project uses [Break Versioning][breakver]. The version numbers follow a
`<major>.<minor>.<patch>` scheme with the following intent:

| Bump    | Intent                                                     |
| ------- | ---------------------------------------------------------- |
| `major` | Major breaking changes -- check the changelog for details. |
| `minor` | Minor breaking changes -- check the changelog for details. |
| `patch` | No breaking changes, ever!!                                |

`-SNAPSHOT` versions are preview versions for upcoming releases.

[breakver]: https://github.com/ptaoussanis/encore/blob/master/BREAK-VERSIONING.md

## 0.1.2

- Upgrade Go version to 1.25.6 and github.com/open-policy-agent/conftest to
  0.66.0
[7705c7b](https://github.com/ilmoraunio/pod-ilmoraunio-conftest/commit/7705c7b34990e639c90264838c7c7e9a87a2ec67)
- Upgrade CI dependencies: babashka to 1.12.214, actions/checkout &
  actions/setup-go to v6, DeLaGuardo/setup-clojure to 13.5.2 + sync Go versions
across platforms
[51b50ee](https://github.com/ilmoraunio/pod-ilmoraunio-conftest/commit/51b50eeab84017d7449292884fa3aa7d0150ccc1)

## 0.1.1

- upgrade github.com/open-policy-agent/conftest to 0.61.2 [d0196cd](https://github.com/ilmoraunio/pod-ilmoraunio-conftest/commit/d0196cd69bc76c1fbee5e901f41e794c96db354d)

## 0.1.0

This is essentially a rehash of 0.0.4, but this one communicates breaking
changes.

- BREAKING: Bump dependency versions for conftest, babashka, go, & for CI dependencies [#4](https://github.com/ilmoraunio/pod-ilmoraunio-conftest/pull/4)
  - The output of HCL2 changes slightly. See
    [open-policy-agent/conftest#1074](https://github.com/open-policy-agent/conftest/pull/1074)
    and
    [open-policy-agent/conftest#1006](https://github.com/open-policy-agent/conftest/issues/1006)
    for more info.
- Document configuration format parse outputs [#5](https://github.com/ilmoraunio/pod-ilmoraunio-conftest/pull/5)

## 0.0.4

Edit: This version unintentionally contained breaking changes, see notes under
[0.1.0](#010).

- Bump dependency versions for conftest, babashka, go, & for CI dependencies [#4](https://github.com/ilmoraunio/pod-ilmoraunio-conftest/pull/4)

## 0.0.3

- Add linux arm64 build [#2](https://github.com/ilmoraunio/pod-ilmoraunio-conftest/pull/2)

## 0.0.2

- Add parse-as for explicit parser support [101db8b](https://github.com/ilmoraunio/pod-ilmoraunio-conftest/commit/101db8b5cea2afc7f5ed704423ffa32594c9ef7e)

## 0.0.1

First release! 🎉
