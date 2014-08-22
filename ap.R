#!/usr/bin/Rscript
# R script for AP

## install AP-Cluster library
install.packages('apcluster', repos='http://cran.us.r-project.org')
library(apcluster)

## Load data
mydata = read.csv("ListenerFeatures.csv")

## Clear Data
mydata$TS <- NULL
mydata$FN <- NULL
mydata$F7 <- NULL

## Get basic features
f1 <- as.double(as.character(mydata$F1))
f2 <- as.double(as.character(mydata$F2))

Average <- f1
StandardDeviation <- f2

## Get Advanced features
f3 <- as.double(as.character(mydata$F3))
f4 <- as.double(as.character(mydata$F4))
f5 <- as.double(as.character(mydata$F5))
f6 <- as.double(as.character(mydata$F6))

Quality <- f3
Lag5 <- f4
Sign <- f5
SignalValue <- f6

## Cluster & Plot
fs <- cbind( Average, StandardDeviation, SignalValue )
apfs <- apcluster( negDistMat(r=2), fs )
plot(apfs, fs)

## Save to file
png("1.png", width=10000, height=10000, res=600)
plot(apfs, fs)
dev.off()

## Cluster & Plot
fs <- cbind( Quality, Lag5, Sign, SignalValue )
apfs <- apcluster( negDistMat(r=2), fs )
plot(apfs, fs)

## Save to file
png("2.png", width=10000, height=10000, res=600)
plot(apfs, fs)
dev.off()
