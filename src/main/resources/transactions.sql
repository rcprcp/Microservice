INSERT INTO transactions (account_number, symbol, transaction_type, price, shares, timestamp) VALUES
-- account 27 - nothing in range for Dec/01 - Dec/08
(27, 'AAPL', 'BUY', 100.0, 1, '2021-11-23 00:00:00'),
(27, 'AAPL', 'BUY', 100.0, 1, '2021-11-24 00:00:00'),
(27, 'AAPL', 'BUY', 100.0, 1, '2021-11-25 00:00:00'),
(27, 'AAPL', 'BUY', 100.0, 1, '2021-11-26 00:00:00'),
(27, 'AAPL', 'BUY', 100.0, 1, '2021-11-27 00:00:00'),
(27, 'AAPL', 'SELL', 100.0, 5, '2021-11-28 00:00:00'),
-- account 28 case one,  before and after window
(28, 'IBM', 'BUY', 100.0, 1, '2021-11-27 00:00:00'),
(28, 'IBM', 'SELL', 100.0, 1, '2021-12-10 00:00:00'),
-- account 29 case two buy before and no sell.
(29, 'TSLA', 'BUY', 100.0, 1, '2021-11-23 00:00:00'),
(29, 'TSLA', 'BUY', 100.0, 1, '2021-11-24 00:00:00'),
-- account 30 case three, buy in the window, sell in the window.
(30, 'TSLA', 'BUY', 100.0, 1, '2021-12-05 00:00:00'),
(30, 'TSLA', 'SELL', 120.0, 1, '2021-12-07 00:00:00'),
-- account 31 case four, buy before the window, no sell
(31, 'TSLA', 'BUY', 100.0, 1, '2021-11-23 00:00:00'),
-- account 32 - case five - multiple buys in the window - no sells
(32, 'TSLA', 'BUY', 100.0, 1, '2021-12-05 00:00:00'),
(32, 'TSLA', 'BUY', 100.0, 1, '2021-12-06 00:00:00'),
(32, 'SNOW', 'BUY', 100.0, 1, '2021-12-07 00:00:00'),
-- account 33 - case six - multiple buys in the window - one sell for all
(33, 'SNOW', 'BUY', 100.0, 1, '2021-12-02 00:00:00'),
(33, 'SNOW', 'BUY', 100.0, 1, '2021-12-03 00:00:00'),
(33, 'SNOW', 'BUY', 100.0, 1, '2021-12-04 00:00:00'),
(33, 'SNOW', 'SELL', 120.0, 3, '2021-12-07 00:00:00'),
-- account 34 - case seven- multiple buys in the window - one sell - not all.
(34, 'SNOW', 'BUY', 100.0, 1, '2021-12-02 00:00:00'),
(34, 'SNOW', 'BUY', 100.0, 1, '2021-12-03 00:00:00'),
(34, 'SNOW', 'BUY', 100.0, 1, '2021-12-04 00:00:00'),
(34, 'SNOW', 'SELL', 120.0, 2, '2021-12-07 00:00:00');
