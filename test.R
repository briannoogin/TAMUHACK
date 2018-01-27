library(twitteR) #installs TwitteR library (twitteR) #loads TwitteR
api_key <- "S9u9eLhjCKlyB2rKYB0Vm0ioA" #in the quotes, put your API key 
api_secret <- "1pPtm5bwpFRuNpwX5WcQ8hBHjCQNgtsuKOAHygPDKqJ1Hcb3EL" #in the quotes, put your API secret token 
token <- "957327348533284864-kClJhTjnirgiZMqxuM5KpPBvZkWyMrt" #in the quotes, put your token
token_secret <- "FmlUTkRmaFOkwSlUWfptcL60QKgTbGeAOoLzEmSypNgwC" #in the quotes, put your token secret 
setup_twitter_oauth(api_key, api_secret, token, token_secret)
tweets <- searchTwitter("hurricane OR #hurricane OR harvey OR #hurricaneharvey", n = 3000, lang = "en")
#tweets.df <-twListToDF(tweets)
write.csv(twListToDF(tweets), "C:\\Users\\socce\\Documents\\TamuHackTest\\tweets.csv") #an example of a file extension of the folder in which you want to save the .csv file.

