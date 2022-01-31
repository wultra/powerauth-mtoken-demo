# Demo of Wultra Mobile Token

[![GitHub issues](https://img.shields.io/github/issues/wultra/powerauth-mtoken-demo.svg?maxAge=2592000)](https://github.com/wultra/powerauth-mtoken-demo/issues)
[![Twitter](https://img.shields.io/badge/twitter-@wultra-blue.svg?style=flat)](http://twitter.com/wultra)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](./LICENSE)

A demo web application that authenticates users via Wultra Mobile Token.

## Installation

1. Install JDK 11
2. Install Maven 3.6
3. Create an API Key at https://sendgrid.com/
4. Clone this repository
5. Run `mvn clean package spring-boot:repackage`
6. Configure the following application properties:
   * `sendgrid.api-key` to the SendGrid API Key
7. Run `java -jar target/mtoken-0.0.1-SNAPSHOT.jar`

## Documentation

The documentation is available at the [Wultra Developer Portal](https://developers.wultra.com/products/mobile-token).

# License

All sources are licensed using the Apache 2.0 license. You can use them with no restrictions.

# Contact

If you need any assistance, do not hesitate to drop us a line at [hello@wultra.com](mailto:hello@wultra.com) or our official [gitter.im/wultra](https://gitter.im/wultra) channel.
