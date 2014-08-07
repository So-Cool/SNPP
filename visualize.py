#! /usr/bin/python

import numpy as np
from numpy.random import randn
import matplotlib.pyplot as plt

import sys
import csv
from pprint import pprint

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


if __name__ == '__main__':
	# extract the collumns from files
	extractCols( sys.argv )

	# for each file
	for oneFile in filesCols:
		# get the number of usefull arguments
		arguments = int(float(oneFile[-2][1]))

		# for each collumn
		for col in oneFile[:arguments]:
			# graph of each feature along time STAMP
			print col
			pass
	
	# graph of clusters 1x1 for each feature

	# np.random.seed(9221999)
	# data = randn(75)
	# plt.hist(data);
	# plt.show()
