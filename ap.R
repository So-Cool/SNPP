# R script for AP
## install AP-Cluster library
install.packages("apcluster")
library(apcluster)
## Load data
mydata = read.csv("ListenerFeatures-1.csv")
## Clear Data
mydata$TS <- NULL
mydata$FN <- NULL
mydata$F7 <- NULL
mydata$F6 <- NULL

m1$F3 <- NULL
m1$F4 <- NULL

m1 <- mydata

f1 <- as.double(as.character(m1$F1))
f2 <- as.double(as.character(m1$F2))
f5 <- as.double(as.character(m1$F5))

Average <- f1
StandardDeviation <- f2
SignalValue <- f5

fs <- cbind( Average, StandardDeviation, SignalValue )
apfs <- apcluster( negDistMat(r=2), fs )
plot(apfs, fs)

## Data 2

mydata = read.csv("ListenerFeatures.csv")
mydata$TS <- NULL
mydata$FN <- NULL
m2 <- mydata
m2$F1 <- NULL
m2$F2 <- NULL

f3 <- as.double(as.character(m2$F3))
f4 <- as.double(as.character(m2$F4))
f5 <- as.double(as.character(m2$F5))
f6 <- as.double(as.character(m2$F6))

Quality <- f3
Lag5 <- f4
Sign <- f5
SignalValue <- f6

fs <- cbind( Quality, Lag5, Sign, SignalValue )
apfs <- apcluster( negDistMat(r=2), fs )
plot(apfs, fs)

# m1$F3 <- NULL
# m1$F4 <- NULL
# m2 <- mydata

## E.G.
# cl1 <- cbind( rnorm(30, 0.3, 0.05), rnorm(30, 0.7, 0.04) )
# cl2 <- cbind( rnorm(30, 0.7, 0.04), rnorm(30, 0.4, 0.05) )
# x1 <- rbind( cl1, cl2 )
# plot( x1, xlab="", ylab="", pch=19, cex=0.8 )
# apres1a <- apcluster( negDistMat(r=2), x1 )
# plot(apres1a, x1)
