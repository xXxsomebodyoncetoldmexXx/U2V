# Simple Burp Suite Extension for converting unicode escape/url encode vietnamese text to UTF-8.

for examples:
- `Tr\u1EDDi%20h%F4m%20nay%20nhi\u1EC1u%20m%E2y%20c\u1EF1c%20\u0110\u1EB7t%20b%E0n%20tay%20m%ECnh%20l%EAn%20ng\u1EF1c` -> `Trời hôm nay nhiều mây cực Đặt bàn tay mình lên ngực`
- `\u0054\u1EEB\u0020\u1EA5\u0079\u0020\u0074\u0072\u006F\u006E\u0067\u0020\u0074\u00F4\u0069\u0020\u0062\u1EEB\u006E\u0067\u0020\u006E\u1EAF\u006E\u0067\u0020\u0068\u1EA1\u0020\u004D\u1EB7\u0074\u0020\u0074\u0072\u1EDD\u0069\u0020\u0063\u0068\u00E2\u006E\u0020\u006C\u00FD\u0020\u0063\u0068\u00F3\u0069\u0020\u0071\u0075\u0061\u0020\u0074\u0069\u006D` -> `Từ ấy trong tôi bừng nắng hạ Mặt trời chân lý chói qua tim`

## How to compile
- Download the latest Gradle and config your intellij to use that Gradle.
- Run `gradle shadowJar`
- Enjoy

## How to use
- There will be a small tab right beside `Pretty`, `Raw` and `Hex`. Change to it and the body content will be converting.

# TODO
- Fix recursion error.
- Converting data in URL.
- Fix performance issue when the number of request need to be converting is too high.
- Write a better README.
