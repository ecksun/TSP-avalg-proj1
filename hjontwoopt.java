public void swap(int a, int b)
{
    if (a==b) return;
    int t = tour[a];
    tour[a] = tour[b];
    tour[b] = t;
    reverse[tour[a]] = a;
    reverse[tour[b]] = b;
}


public void reverse(int start, int end)
{
    //System.err.println("reverse\t"+start+"\t"+end);
    int len = end - start;

    if (len < 0) // vi har en wraparound
    {
        len += tour.length;
    }

    len = (len+1)/2; // vi behöver bara swappa "hälften av längden" ggr, eftersom vi tar 2 element per swap (+1 för att inte udda antal element ska lämnas kvar)

    for (int k = 0; k < len; k++)
    {
        swap(start, end);

        // wraparound för start & end
        if (++start == tour.length)
            start = 0;

        if (--end <= 0)
            end = tour.length-1;
    }
}

public void twooptmove(int a, int b)
{
    // gör en two-opt move på a och b, antingen genom att vända på allt mellan (och inkl.) a och b eller
    // allt mellan b+1 och a-1
    int aindex = getindex(a);
    int bindex = getindex(b);
    int am1index = getindex(prev(a));
    int bp1index = getindex(next(b));

    if (Math.abs(bindex-aindex) < Math.abs(bp1index-am1index))
        reverse(aindex, bindex); // den kortaste vägen är mellan a och b
    else
        reverse(bp1index,am1index); // annars mellan b+1 och a-1

}
