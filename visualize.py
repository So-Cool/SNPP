#! /usr/bin/python

import numpy as np
from numpy.random import randn
import matplotlib.pyplot as plt

import sys
import csv
from pprint import pprint

import time
import datetime

# CSV files to visualize
filesCols = []

## open files and extract collumns
def extractCols( fileArgs ) :
	for i in range( 1, len(list(fileArgs)) ):
		filesCols.append( [] )
		with open(list(fileArgs)[i], 'rb') as csvfile:
			reader = csv.reader(csvfile, delimiter=',', quotechar='|')

			for j in reader:
				for k in j:
					filesCols[-1].append([])
				break

			for row in reader:
				if row == []:
					continue
				for j in range( len(row) ):
					filesCols[-1][j].append( row[j] )
				# print ', '.join(row)		
	# pprint( filesCols )

# change US format date to time-stamp
def dateToStamp( dateList ):
	stamps = []
	for date in dateList:
		d1 = date.split()[:-2]
		d2 = []
		d2.append( date.split()[-1] )
		d3 = " ".join(d1 + d2)
		stamps.append( time.mktime(datetime.datetime.strptime( d3, "%c" ).timetuple()) )
	return stamps

# scan collumns for abnormalities
def scanCols():
	for i in range( len(filesCols) ):
		for j in range( len(filesCols[i]) ):
			for k in range( len(filesCols[i][j]) ):
				tr = filesCols[i][j][k]
				filesCols[i][j][k] = float('nan') if ( tr == "?" ) else tr


if __name__ == '__main__':
	# extract the collumns from files
	extractCols( sys.argv )

	# scan collumns for abnormalities, i.e. '?'
	scanCols()

	# for each file
	for oneFile in filesCols:
		# get time stamps
		stamps = dateToStamp( oneFile[-1] )

		# get the number of usefull arguments
		arguments = int(float(oneFile[-2][1]))

		# for each collumn
		for i, col in enumerate( oneFile[:arguments]):
			# print zip( stamps, col )
			# graph of each feature along time STAMP
			plt.figure(i)
			plt.plot(stamps,col)
			plt.scatter(stamps,col)
			plt.xlabel("time stamp")
			plt.ylabel("value")
			plt.title("Feature " + str(i+1) + " in time")
			
			# set constant time axes
			ax = plt.axis()
			dif = stamps[1] - stamps[0]
			plt.axis( [ stamps[0]-dif, stamps[-1]+dif, ax[2], ax[3] ] )

			plt.savefig("feature"+str(i+1)+".png", dpi=300, pad_inches=0.2)
			plt.show()

			# now do the animation
			plt.ion()
			plt.figure(50)
			plt.axis( [ stamps[0]-dif, stamps[-1]+dif, ax[2], ax[3] ] )
			oldTime = stamps[0]
			ex = []
			ey = []
			for x, y in zip(stamps, col):
				ex.append(x)
				ey.append(y)

				plt.plot(ex, ey, "ro")
				plt.plot(ex, ey, "b-")
				plt.draw()
				print "Point! ",
				sys.stdout.flush()

				# time.sleep( x-oldTime )
				plt.pause(x-oldTime)
				oldTime = x
			print ""
			plt.ioff()
			plt.show()

	
	# graph of clusters 1x1 for each feature
	for oneFile in filesCols:

		# get the number of usefull arguments
		arguments = int(float(oneFile[-2][1]))

		for i, col1 in enumerate( oneFile[:arguments]):
			for j, col2 in enumerate( oneFile[:arguments]):

				# do not print x to x
				if i == j or j < i :
					continue

				# print zip( col1, col2 )
				# graph of each feature along time STAMP
				plt.figure(7+i)
				plt.scatter(col1,col2)
				plt.xlabel( "Feature " + str(i+1) )
				plt.ylabel( "Feature " + str(j+1) )
				plt.title("Feature " + str(i+1) + " against feature " + str(j+1))
				# plt.axis([ 0, 1, -0.075, -0.035 ])
				plt.savefig("f"+str(i+1)+"f"+str(j+1)+".png", dpi=300, pad_inches=0.2)
				plt.show()
				# !!!!!!!!!!! GIVE COLOURS TO POINTS HERE !!!!!!!!!!!!!!! #
