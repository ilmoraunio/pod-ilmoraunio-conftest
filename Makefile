.PHONY: default
default: build

.PHONY: build
build:
	go build

.PHONY: test
test:
	./scripts/test
