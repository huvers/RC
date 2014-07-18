package com.hbar.finance.math.models;


import java.awt.*;     // Abstract Windowing Toolkit
import java.applet.*;  // needed by applets
import java.lang.Math; // as we do some math (sqr etc.)
import java.util.*;    // for Vector



// cenlim is the "main" class.
// All applets derive from class Applet.
public class CentralLimitTheoremDemoApplet extends Applet {

    // for layout we use 6 panels
    // a panel is a region on the screen
    Panel upperLeft;
    Panel upperMiddle;
    Panel upperRight;
    Panel lowerLeft;
    Panel lowerMiddle;
    Panel lowerRight;

    // in upperLeft panel:
    DensityFunctionPanel df;
    Label dfTitle;
    Label dfStatus;

    // in upperMiddle panel
    CDFPanel cdf;
    Label cdfTitle;
    Label cdfStatus;

    // in upperRight panel
    ItalicLabel SubTitle1;
    Label nLabel;
    IChoice nChoice;
    ItalicLabel SubTitle2;
    Label SimLenLabel;
    IChoice SimLenChoice;
    Button runbutton;
    ProgressBar progress;

    // background color of bottom panels
    public final static Color plotcolor = new Color(128,255,128);

    // bottom panels
    static final int NOSUMS = 3;  // number of summation potters
    SumPlotPanel SumPlot[] = new SumPlotPanel[NOSUMS];
    static public IChoice SetSumLen[] = new IChoice[NOSUMS];

    public void init() { // called by the system


        super.init();

        /*(String s = getParameter("Language");
        if (s.compareTo("Nederlands") == 0)
            Strings.SetLanguage(Strings.NEDERLANDS);
        else*/
            Strings.SetLanguage(Strings.ENGLISH);


        setLayout(new GridLayout(2,3)); // 2 rows, 3 columns

        upperLeft = new Panel();
        upperLeft.setLayout(new BorderLayout());
           // within the upperLeft panel we use a borderLayout (i.e.
           // a "flexible" Center, surrounded by North, West, East, South)

        upperMiddle = new Panel();
        upperMiddle.setLayout(new BorderLayout());

        upperRight = new Panel();
        upperRight.setLayout(new FlowLayout(FlowLayout.LEFT,5,1)); // default

        lowerLeft = new Panel();
        lowerLeft.setLayout(new BorderLayout());

        lowerMiddle = new Panel();
        lowerMiddle.setLayout(new BorderLayout());

        lowerRight = new Panel();
        lowerRight.setLayout(new BorderLayout());

        // set colors
        upperLeft.setBackground(Color.white);
        upperMiddle.setBackground(Color.lightGray);
        upperRight.setBackground(Color.white);
        lowerLeft.setBackground(plotcolor);
        lowerMiddle.setBackground(plotcolor);
        lowerRight.setBackground(plotcolor);
        //lowerLeft.setForeground(Color.white);
        //lowerMiddle.setForeground(Color.white);
        //lowerRight.setForeground(Color.white);

        addPanelElements();
            // here we add elements to the individual panels

        add(upperLeft);
        add(upperMiddle);
        add(upperRight);
        add(lowerLeft);
        add(lowerMiddle);
        add(lowerRight);



    }


    public void addPanelElements() {

        // upperLeft Panel
        // center will stretch if applet is resized
        df = new DensityFunctionPanel(this);
        upperLeft.add("Center",df);
        dfTitle = new Label(Strings.get(Strings.DISTRIBUTION),Label.CENTER);
        upperLeft.add("North",dfTitle);
        dfStatus = new Label(Strings.get(Strings.MOVE_POINTS),Label.CENTER);
        upperLeft.add("South",dfStatus);

        // upperMiddle Panel
        cdf = new CDFPanel(df.ControlPoints);
        upperMiddle.add("Center",cdf);
        cdfTitle = new Label(Strings.get(Strings.CUM_DIST),Label.CENTER);
        upperMiddle.add("North",cdfTitle);
        cdfStatus = new Label("");
        upperMiddle.add("South",cdfStatus);


        // upperRight Panel
        // we use a FlowLayout here. The problem is there is no
        // direct way to tell the layout manager that the next
        // component should appear on a new line. We have used the
        // trick to introduce a dummy EndOfRow panel with height 0
        // and a large width.
        SubTitle1 = new ItalicLabel(Strings.get(Strings.DISTRIBUTION));
        upperRight.add(SubTitle1);
        upperRight.add(new EndOfRow());
        nLabel = new Label(Strings.get(Strings.NUM_POINTS));
        upperRight.add(nLabel);
        int iarray[] = {3,4,5,6,7,8,9,10,11,12,13,14};
        nChoice = new IChoice(iarray,6);
        upperRight.add(nChoice);
        upperRight.add(new EndOfRow());
        SubTitle2 = new ItalicLabel(Strings.get(Strings.SIMULATION));
        upperRight.add(SubTitle2);
        upperRight.add(new EndOfRow());
        SimLenLabel = new Label(Strings.get(Strings.SIM_LEN));
        upperRight.add(SimLenLabel);
        int karray[] = {100,200,500,1000, 5000, 10000};
        SimLenChoice = new IChoice(karray,1000);
        upperRight.add(SimLenChoice);
        upperRight.add(new EndOfRow());
        runbutton = new Button(Strings.get(Strings.START_SIMULATION));
        upperRight.add(runbutton);
        upperRight.add(new EndOfRow(10));
        progress = new ProgressBar();
        progress.setForeground(Color.blue);
        upperRight.add(progress);


        // lower Panels
        int SumLen[] = {1,2,5,10,20,50}; // Choices for summation lengths
        int InitSumLen[] = {1,2,10};  // Defaults for summation lengths (size=NOSUMS)
        for (int i=0; i<NOSUMS; ++i) {
            SumPlot[i] = new SumPlotPanel(this);
            SetSumLen[i] = new IChoice(SumLen, InitSumLen[i]);
        }


        // lowerLeft Panel
        lowerLeft.add("North",new Label(Strings.get(Strings.RESULT),Label.CENTER));
        lowerLeft.add("Center",SumPlot[0]);
        Panel p = new Panel();
        p.add(new Label(Strings.get(Strings.SUM_LEN)));
        p.add(SetSumLen[0]);
        lowerLeft.add("South",p);

        // lowerMiddle Panel
        lowerMiddle.add("North",new Label(Strings.get(Strings.RESULT),Label.CENTER));
        lowerMiddle.add("Center",SumPlot[1]);
        p = new Panel();
        p.add(new Label(Strings.get(Strings.SUM_LEN)));
        p.add(SetSumLen[1]);
        lowerMiddle.add("South",p);

        // lowerRight Panel
        lowerRight.add("North",new Label(Strings.get(Strings.RESULT),Label.CENTER));
        lowerRight.add("Center",SumPlot[2]);
        p = new Panel();
        p.add(new Label(Strings.get(Strings.SUM_LEN)));
        p.add(SetSumLen[2]);
        lowerRight.add("South",p);


    }


    //  ClearSumPlots: clear bottom row panels
    //  called as soon as we change something in the distribution, because
    // then pics don't correspond anymore.
    public void ClearSumPlots() {
        for (int i=0; i<NOSUMS; ++i) {
            SumPlot[i].Clear();
        }
    }


    // simulate: the work horse
    public void simulate() {

             // get simulation length
             int n = SimLenChoice.SelectedItem();

             // number of segments
             CDFsegmentVector segVec = cdf.segVec;
             int nsegs = segVec.size();


             // y-range
             double maxy = segVec.segmentAt(nsegs-1).y2;
             // run simulation
             double observ[] = new double[n];   // store draws in here
             Random rand = new Random();
             for (int k=0; k<n; ++k) {

                // every now and then update the progress bar
                if (k % 100 == 0)
                    progress.setPerc((k*1.0)/n);

                // in case of error
                observ[k] = -1;

                double y = rand.nextDouble()*maxy;
                // find corresponding x in piecewise quadratic
                // function....
                // for now do a stupid linear search
                int i = segVec.findSegmentY(y);
                if (i == -1) {
                    System.out.println("Could not find "+y);
                    continue;
                }
                CDFsegment seg = segVec.segmentAt(i);
                double discr = Math.sqrt(seg.b*seg.b-4*seg.a*(seg.c-y));
                double x1 = (-seg.b+discr)/(2*seg.a);
                double x2 = (-seg.b-discr)/(2*seg.a);
                if ((x1 >= seg.x1) && (x1 <= seg.x2)) {
                    observ[k] = x1;
                    continue;
                }
                if ((x2 >= seg.x2) && (x2 <= seg.x2)) {
                    observ[k] = x2;
                    continue;
                }
                System.out.println("Error");

             }

            // now do the summations and create the statistics
            int nsums = 3;
            int ninterval = 50;
            int sumlen[] = new int[3];
            for (int i = 0; i<3; ++i)
                sumlen[i] = CentralLimitTheoremDemoApplet.SetSumLen[i].SelectedItem();
            int sumk[] = {0, 0, 0};
            double sum[] = {0.0, 0.0, 0.0};
            int count[][] = new int[nsums][ninterval];



            for (int k=0; k<n; ++k) {
                if (observ[k] < 0.0)
                    continue;
                for (int kk=0; kk<nsums; ++kk) {
                    ++sumk[kk];
                    sum[kk] += observ[k];
                    if (sumk[kk] == sumlen[kk]) {
                        // find bucket
                        int bucket = (int) ((sum[kk]*ninterval)/sumlen[kk]);
                        if ((bucket >=0) && (bucket < ninterval))
                            ++count[kk][bucket];
                        // reset
                        sum[kk] = 0.0;
                        sumk[kk] = 0;
                    }

                }
            }

            // get rid of progress bar as we are done
            progress.Reset();

            // draw the resulting empirical distributions
            for (int i=0; i<nsums; ++i)
                SumPlot[i].Histogram(count[i], ninterval, sumlen[i]);

    }


    public boolean handleEvent(Event event) {
        switch(event.id) {

            case Event.ACTION_EVENT:

               // number of control points is changed
               if (event.target == nChoice) {
                  Integer i = new Integer((String)event.arg);
                  df.SetNumberOfPoints(i.intValue());
                  cdf.repaint();
                  ClearSumPlots();
                  return true;
               }
               // RUN button is pressed
               else if (event.target == runbutton) {
                  simulate();
                  return true;
               }
               else
                return false;

             default:
                return false;
        }

    }
}



class Strings extends Object {

    // languages
    static final int UNKNOWN = 0;
    static final int NEDERLANDS = 1;
    static final int ENGLISH = 2;
    static int language = UNKNOWN;

    // strings
    static final int DISTRIBUTION = 0;
    static final int MOVE_POINTS = 1;
    static final int CUM_DIST = 2;
    static final int NUM_POINTS = 3;
    static final int SIMULATION = 4;
    static final int SIM_LEN = 5;
    static final int START_SIMULATION = 6;
    static final int RESULT = 7;
    static final int SUM_LEN = 8;


    static final String NED[] = {
        "verdeling",
        "verplaats punten",
        "cumulatieve verdelingsfunctie",
        "Aantal punten:",
        "simulatie",
        "Simulatielengte:",
        "Start Simulatie",
        "Resultaat",
        "Sommatielengte:"

    };
    static final String ENG[] = {
        "density",
        "move points",
        "cumulative distribution",
        "Number of points:",
        "simulation",
        "Simulation length:",
        "Start Simulation",
        "Result",
        "Summation length:"

    };

    public static void SetLanguage(int lang) {
        language = lang;
    };

    public static String get(int s) {
        switch (language) {
            case NEDERLANDS:
                return(NED[s]);
            case ENGLISH:
                return(ENG[s]);
            default:
                return(ENG[s]);
        }
    };
}


class DensityFunctionPanel extends Panel {

     // constants are declared as "private final"
    private final int InitialPoints = 6;
    // vector with control points
    public ControlPointVector ControlPoints =
        new ControlPointVector(InitialPoints, 0.0, 1.0);
    // mapping object
    private Mapping m = new Mapping();
    // point currently being dragged
    // -1 means: there is no "current point"
    private int CurPoint = -1;
    // for access to cenlim (main class)
    CentralLimitTheoremDemoApplet MyApp;


    public DensityFunctionPanel(CentralLimitTheoremDemoApplet application) {
        super();               // Panel constructor
        MyApp = application;   // save reference to "cenlim" application
    }

    public void paint(Graphics g) { // called by applet viewer

        // set color and paint mode
        g.setColor(getForeground());
        g.setPaintMode();

        // draw a border around the canvas
        g.drawRect(0,0,size().width-1,size().height-1);

        // we draw in area (0,0)-(1,1)
        // create drawing area that is a little bit larger
        m.SetSize(-0.1d, -0.1d, 1.1d, 1.1d,
                   0, 0, size().width, size().height);

        // draw x-axis (0,0)-(1,0)
        m.drawLine(g, 0.0d, 0.0d, 1.0, 0.0);

        // draw control points and connecting polygon
        ControlPoints.Paint(g,m);

    }


    public boolean handleEvent(Event e) {
        switch (e.id) {

            case Event.MOUSE_DOWN:
                // locate current point
                CurPoint = ControlPoints.FindPoint(m,e.x,e.y);
                return true;

            case Event.MOUSE_DRAG:
                // if we have a "current point", move it
                if (CurPoint >= 0) {
                    ControlPoints.SetY(m, e.y, CurPoint, 0.0, 1.0);
                    repaint(); // repaint ourselves
                    MyApp.cdf.repaint(); // update the cdf
                    MyApp.ClearSumPlots(); // clear bottom row panels
                }
                return true;

            case Event.MOUSE_UP:
                // we no longer have a "current point"
                CurPoint = -1;
                return true;

            default:
                return false;
        }
    }


    // number of control points must change
    public void SetNumberOfPoints(int i) {
        ControlPoints.SetSize(i);
        repaint();
    }



}

// EKPoint implements a single control point.
class EKPoint extends Object {

    // size of point in pixels
    private static final int SIZE =4;
    // coordinates of point (in world coordinates)
    public double wx, wy;

    public EKPoint() {} // constructor

    public EKPoint(double a, double b) { // constructor
        wx = a;
        wy = b;
    }

    // Paint uses the mapping m to draw this point
    public void Paint(Graphics g, Mapping m) {
        g.drawRect(m.mapx(wx)-SIZE, m.mapy(wy)-SIZE, SIZE*2, SIZE*2);
    }

    // IsPoint returns true if screen coordinates (x,y)
    // are pointing to the current control point
    public boolean IsPoint(Mapping m, int x, int y) {
        return (x >= m.mapx(wx)-SIZE &&
                x <= m.mapx(wx)+SIZE &&
                y >= m.mapy(wy)-SIZE &&
                y <= m.mapy(wy)+SIZE);
      }

    // SetY resets the y coordinate as a result of user dragging.
    // Make sure we stay within the interval [miny,maxy]
    // (these are in world coordinates)
    public void SetY(Mapping m, int y, double miny, double maxy) {
        wy = m.invmapy(y);
        if (wy<miny)
            wy = miny;
        if (wy>maxy)
            wy = maxy;
    }
}




class ControlPointVector extends Object {

    public Vector v; // vector of Points
    private double lower, upper;


    public ControlPointVector(int InitialSize, double lo, double up) {

        v = new Vector(InitialSize);

        // distribute x evenly over [lo,up]
        // y is always 0.0
        lower = lo;
        upper = up;
        double step = (up-lo)/(InitialSize-1);
        for (int i=0; i<InitialSize; ++i) {
            v.addElement(new EKPoint(lo+step*i, 0.0d));
        }
    }


    public void SetSize(int n) {

        int size = Size();

        if (n==size)  // nothing to do
            return;

        if (n<size)   // size decreases
            v.setSize(n);
        else
            for (int i=size; i<n; ++i)
                v.addElement(new EKPoint(0.0d, 0.0d));

        double step = (upper-lower)/(n-1);
        for (int i = 0; i<n; ++i) {
            EKPoint p = (EKPoint) v.elementAt(i);
            p.wx = lower+step*i;
        }

    }

    public int Size() {
        return v.size();
    }

    public void Paint(Graphics g, Mapping m) {
        EKPoint pprev = new EKPoint();
        pprev.wx = 0.0; // assume we start drawing at (0,0)
        pprev.wy = 0.0;
        for (int i = 0; i<Size(); ++i) {
            EKPoint p = (EKPoint) v.elementAt(i);
            p.Paint(g,m);
            m.drawLine(g, pprev.wx, pprev.wy, p.wx, p.wy);
            pprev = p;
        }
        m.drawLine(g, pprev.wx, pprev.wy, 1.0, 0.0); // assume polygon ends at (1,0)
    }

    // Using a stupid linear search we find the control point
    // that corresponds to screen coordinates (x,y)
    public int FindPoint(Mapping m, int x, int y) {
        for (int i = 0; i<Size(); ++i) {
            EKPoint p = (EKPoint) v.elementAt(i);
            if (p.IsPoint(m, x, y))
                return i;
        }
        return -1;  // failed to find control point
    }


    public void SetY(Mapping m, int y, int pointno, double miny, double maxy) {
        EKPoint p = (EKPoint) v.elementAt(pointno);
        p.SetY(m, y, miny, maxy);
    }
}


// a single segment of the CDF.
// The functional form of this segment is: F(x) = ax^2+bx+c

class CDFsegment extends Object {
    double x1, x2;  // interval
    double y1, y2;  // F(x1), F(x2)
    double a,b,c;   // F(x) = ax^2 + bx + c
    static final int n = 10;   // divide each segment in 10 sub-segments

     public void draw(Graphics g, Mapping m) {

        double dx = x2-x1;
        double step = dx / n;

        for (int i=0; i<n; ++i) {
            double xa = x1 + step*i;
            double xb = xa + step;
            double ya = eval(xa);;
            double yb = eval(xb);
            m.drawLine(g, xa, ya, xb, yb);
        }

    }

    public double eval(double x) {
        return a*x*x+b*x+c;
    }
}

// vector of segments
class CDFsegmentVector extends Vector {


    public CDFsegmentVector(int InitialSize) {
        super(InitialSize);
    }
    public CDFsegment segmentAt(int index) {
        return( (CDFsegment) elementAt(index) );
    }

    public void draw(Graphics g, Mapping m) {
        for (int i=0; i<size(); ++i)
            segmentAt(i).draw(g,m);
    }

    public int findSegmentY(double y) {
        for (int i=0; i<size(); ++i)
            if ((segmentAt(i).y1 <= y) && (y <= segmentAt(i).y2))
                return(i);
        return(-1);

    }

}


// pane with CDF
class CDFPanel extends Panel {

    private final boolean DEBUG = true;
    private Mapping m = new Mapping();
    public ControlPointVector v; // user drawn control points
    public CDFsegmentVector segVec;


    public CDFPanel(ControlPointVector cpv) {
        v = cpv;
    }


    public void paint(Graphics g) {

        g.setColor(getForeground());
        g.setPaintMode();


        // draw a border around the canvas
        g.drawRect(0,0,size().width-1,size().height-1);


        // calculate cdf
        // cdf is piecewise quadratic function
        // for each segment [x1,x2] we provide:
        //      a,b,c such that F(x) =  ax^2 + bx + c
        //      y1 = F(x1)
        //      y2 = F(x2)

        segVec = new CDFsegmentVector(v.Size()-1);
        EKPoint ekp = (EKPoint)v.v.elementAt(0);
        double prevx = ekp.wx;
        double prevy = ekp.wy;
        double area = 0.0;
        for (int i=1; i<v.Size(); ++i) {
             ekp = (EKPoint)v.v.elementAt(i);
             CDFsegment seg = new CDFsegment();

             double dx = ekp.wx-prevx;
             double dy = ekp.wy-prevy;

             seg.x1 = prevx;
             seg.y1 = area;
             seg.x2 = ekp.wx;

             seg.a = 0.5*dy/dx;
             seg.b = prevy-(dy/dx)*prevx;
             seg.c = area - seg.a*prevx*prevx - seg.b*prevx;


             area += seg.a*(ekp.wx*ekp.wx-prevx*prevx) +
                     seg.b*(ekp.wx-prevx);

             seg.y2 = area;
             segVec.addElement(seg);

             if (DEBUG) {
                double y;

                y = seg.eval(seg.x1);
                if (Math.abs(y - seg.y1) > 0.001) {
                    System.out.println("x1="+seg.x1+" y1="+seg.y1+" y="+y);
                    throw new ArithmeticException();
                }

                y = seg.eval(seg.x2);
                if (Math.abs(y - seg.y2) > 0.001) {
                    System.out.println("x2="+seg.x2+" y2="+seg.y2+" y="+y);
                    throw new ArithmeticException();
                }
             }

             prevx = ekp.wx;
             prevy = ekp.wy;
        }


        m.SetSize(-0.1*prevx, -0.1*area, 1.1*prevx, 1.1*area,
                   0,0, size().width, size().height);

        // draw x-axis
        m.drawLine(g, 0.0, 0.0, prevx, 0.0);


        // draw function
        segVec.draw(g,m);

    }


}


//------------------------------------------------------------
// This mapping class implements mapping between "world
// coordinates" (user coordinates) into "screen coordinates"
// (pixels).
// It is implemented for speed, so it may be a bit obscure.
// Basically the mapping (wx,wy) -> (sx,sy) is a linear one:
//       sx := ax*wx + bx;
//       sy := ay*wy + by;
//

class Mapping extends Object {

    private double ax, ay, bx, by;  // mapping parameters

    // map wx->sx
    public int mapx(double x) {
        return( (int) (x*ax+bx) );
    }

    // map wy->sy
    public int mapy(double y) {
        return( (int) (y*ay+by) );
    }

    // map sx->wx (inverse mapping)
    public double invmapx(int x) {
        return( (x-bx)/ax );
    }

    // map sy->wy (inverse mapping)
    public double invmapy(int y) {
        return( (y-by)/ay );
    }

    // setSize is the key routine in this class.
    // Passed on are window size in both world coordinates
    // and screen coordinates. The mapping parameters
    // ax,bx,ay,by are calculated once, so we can handle
    // subsequent mappings quickly.
    // There are virtually no checks, so we assume
    // args are ok.
    public void SetSize(double wx1, double wy1, double wx2, double wy2,
                   int sx1, int sy1, int sx2, int sy2) {
        ax = (sx2-sx1)/(wx2-wx1);
        bx = -wx1*ax;
        ay = -(sy2-sy1)/(wy2-wy1);
        by = sy2-wy1*ay; // y orientation is reversed
    }


    // utility routines
    public void drawLine(Graphics g, double x1, double y1, double x2, double y2) {
       g.drawLine(mapx(x1),mapy(y1),mapx(x2),mapy(y2));
    }

    public void drawRect(Graphics g, double x1, double y1, double x2, double y2) {
       g.drawRect(mapx(x1),mapy(y2),mapx(x2)-mapx(x1),mapy(y1)-mapy(y2));
    }

}


// IChoice is a wrapper around Choice
// Presents a drop down list of integers
class IChoice extends Choice {
    private int iarray[];

    IChoice(int choices[],int def) {
        iarray = choices;
        for (int i=0; i<iarray.length; ++i)
            addItem(""+choices[i]);
        select(""+def);
    }

    public int SelectedItem() {
        return(iarray[getSelectedIndex()]);
    }
}

// Label in Italics
class ItalicLabel extends Label {

    public ItalicLabel() {
        super();
        Italic();
    }
    public ItalicLabel(String label) {
        super(label);
        Italic();
    }
    public ItalicLabel(String label, int alignment) {
        super(label,alignment);
        Italic();
    }

    public void Italic() {
        setFont(new Font("Times",Font.ITALIC+Font.BOLD,12));
    }
}

// In FlowLayout we need something to force the next component to go
// to the next line. We use a dirty trick for that....
// Is there a better solution for this?
class EndOfRow extends Panel {
    private int heigh = 0;

    public EndOfRow(int h) {
        super();
        heigh = h;
    }
    public EndOfRow() {
        super();
    }

    public Dimension preferredSize() {
        return(new Dimension(200,heigh));
    }
}


// these bottom-row panels display a bar chart
class SumPlotPanel extends Panel {

    private Mapping m = new Mapping();
    private int cnt[];
    private double width;
    private double height;
    private Applet app;

    public SumPlotPanel(Applet ap) {
         app = ap;
    }


    public void Clear() {
        if (cnt != null) {
            cnt = null;
            repaint();
        }
    }

    public void Histogram(int count[], int ninterval, int sumlen) {

        cnt = count;

        width = cnt.length;
        height = 0.0;
        for (int i=0; i<cnt.length; ++i) {
            if (count[i]>height)
                height = count[i];
        }

        repaint();

    }

    public void paint(Graphics g) {

        g.setColor(getForeground());
        g.setPaintMode();


        // draw a border around the canvas
        g.drawRect(0,0,size().width-1,size().height-1);


        if (cnt != null) { // there is something to draw

           m.SetSize(-0.1*width, -0.1*height, 1.1*width, 1.1*height,
                   0,0, size().width, size().height);


           // draw x-axis
           m.drawLine(g, 0.0, 0.0, width, 0.0);

           //  draw bars
           for (int i=0; i<cnt.length; ++i)
               m.drawRect(g, i, 0, i+1, cnt[i]);

        }

    }


}

// extremely primitive progress bar.
class ProgressBar extends Canvas {

    private double perc = 0.0;

    public void Reset() {
        perc = 0.0;
        repaint();
    }

    public void paint(Graphics g) {
        Dimension d = size();

        g.setColor(getForeground());
        g.setPaintMode();

        g.fillRect(0, 0, (int) (d.width*perc), d.height-1);

    }

    public void setPerc(double p) {
        perc = p;
        //System.out.println("new perc="+perc);
        update(getGraphics());
    }

    public Dimension preferredSize() {
        return new Dimension(200,20);
    }

    public Dimension minimumSize() {
        return new Dimension(100,20);
    }
}