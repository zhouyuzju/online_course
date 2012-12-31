#include <iostream>
using namespace std;
int main()
{
	double p[8] = {0.0,0.05,0.4,0.08,0.04,0.1,0.1,0.23};
	double A[8][8];
	for(int i = 0;i < 8;i++)
		for(int j = 0;j < 8;j++)
			if(i > j)
				A[i][j] = 0.0;
			else if(i == j)
				A[i][j] = p[i];
	for(int s = 1;s < 7;s++)
		for(int i = 1;i < 8;i++)
		{
			if(i + s >= 8)
				continue;
			double min = 32767.0;
			double pk = 0.0;
			for(int r = i;r <= i + s;r++)
				pk += p[r];
			for(int r = i;r <= i + s && i + s < 8;r++)
			{
				double x = 0.0;
				if(i <= r - 1)
					x = A[i][r - 1];
				double y = 0.0;
				if(r + 1 <= i + s)
					y = A[r + 1][i + s];
				cout << x + y << endl;
				if(min > x + y)
					min = x + y;
			}
			cout << min << "," << pk << endl;
			A[i][i + s] = min + pk;
			cout << i << "," << i + s << "," << A[i][i + s] << endl; 
		}
	cout << A[1][7] << endl;
	return 0;
}
