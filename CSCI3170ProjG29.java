import java.util.Scanner;
import java.sql.*;
import java.io.*;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class CSCI3170ProjG29 {
    // CHANGE START
    public static String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db29";
    public static String dbUsername = "Group29";
    public static String dbPassword = "CSCI3170";
    // CHANGE END

    public static Connection connectToOracle(){
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
        }catch (ClassNotFoundException e){
            System.out.println("[Error]: Java MySQL DB Driver not found!!");
            System.exit(0);
	    }catch (SQLException e){
            //System.out.println(e);
	    }
	    return con;
    }

    public static void CreatePassengers()throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS Passengers " +
			"(PassengerID INTEGER, " +
			" PassengerName CHAR(30), " +
			" PRIMARY KEY (PassengerID))";
	    Connection mySQLDB = connectToOracle();
	    Statement stmt = mySQLDB.createStatement();
	    stmt.executeUpdate(sql);
	    //System.out.println("Created table Passengers in given database...");
    }
    public static void CreateRequests()throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS Requests " +
                    "(RequestID INTEGER AUTO_INCREMENT, " +
                    " PassengerID INTEGER," +
                 	" ModelYear INTEGER,"+
                 	" Model CHAR(30),"+
 			        " SeatOrder INTEGER, " +
 			        " Taken BOOLEAN, "+
 			        " PRIMARY KEY (RequestID))";
 	    Connection mySQLDB = connectToOracle();
 	    Statement stmt = mySQLDB.createStatement();
 	    stmt.executeUpdate(sql);
 	   // System.out.println("Created table Requests in given database...");
    }
    public static void CreateTrips()throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS Trips " +
			        "(TripID INTEGER AUTO_INCREMENT, " +
                    " DriverID INTEGER," +
                    " PassengerID INTEGER," +
			    " Start DATETIME, " +
		    	" End DATETIME, " +
                " Fee INTEGER," +
			    " Rating INTEGER," +
			    " PRIMARY KEY (TripID))";
	    Connection mySQLDB = connectToOracle();
	    Statement stmt = mySQLDB.createStatement();
	    stmt.executeUpdate(sql);
	    //System.out.println("Created table Trips in given database...");
    }
    public static void CreateDrivers()throws SQLException{
	    String sql = "CREATE TABLE IF NOT EXISTS Drivers " +
			        "(DriverID INTEGER, " +
			        " DriverName CHAR(30), " +
                    " VehicleID CHAR(6)," +
			        " PRIMARY KEY (DriverID))";
	    Connection mySQLDB = connectToOracle();
	    Statement stmt = mySQLDB.createStatement();
	    stmt.executeUpdate(sql);
	    //System.out.println("Created table Drivers in given database...");
    }
    public static void CreateVehicles()throws SQLException{
	    String sql = "CREATE TABLE IF NOT EXISTS Vehicles " +
			        "(VehicleID CHAR(6), " +
			        " Model CHAR(30), " +
			        " ModelYear INTEGER, " +
			        " Seats INTEGER, "+
			        " PRIMARY KEY (VehicleID))";
	    Connection mySQLDB = connectToOracle();
	    Statement stmt = mySQLDB.createStatement();
	    stmt.executeUpdate(sql);
	    //System.out.println("Created table Vehicles in given database...");
    }
    public static void CreateTable() throws SQLException{
        CreateDrivers();
        CreateVehicles();
        CreatePassengers();
        CreateRequests();
        CreateTrips();
        System.out.println("Processing...Done! Tables are created!");
    }//1.1
    public static void DeleteTable() throws SQLException{
        Connection mySQLDB = connectToOracle();
	    Statement stmt = mySQLDB.createStatement();
        String sql = "DROP TABLE Drivers";
        stmt.executeUpdate(sql);
        sql = "DROP TABLE Vehicles";
        stmt.executeUpdate(sql);
        sql = "DROP TABLE Passengers";
        stmt.executeUpdate(sql);
        sql = "DROP TABLE Requests";
        stmt.executeUpdate(sql);
        sql = "DROP TABLE Trips";
        stmt.executeUpdate(sql);
	    System.out.println("Processing...Done! All Tables are deleted!");
    }//1.2
    public static void LoadData() throws SQLException{

        System.out.println("Please enter the folder path.");
        Scanner keyboard = new Scanner(System.in);
        String path = keyboard.nextLine();

        try{
            Connection mySQLDB = connectToOracle();
	        Statement stmt = mySQLDB.createStatement();
            String sql = "LOAD DATA LOCAL INFILE '%s/passengers.csv' INTO TABLE Passengers " +
			            "FIELDS TERMINATED BY ',' " +
			            "LINES TERMINATED BY '\\n' ";
            sql = String.format(sql, path);
            stmt.executeUpdate(sql);

            sql = "LOAD DATA LOCAL INFILE '%s/drivers.csv' INTO TABLE Drivers " +
			            "FIELDS TERMINATED BY ',' " +
			            "LINES TERMINATED BY '\\n' ";
            sql = String.format(sql, path);
            stmt.executeUpdate(sql);

            sql = "LOAD DATA LOCAL INFILE '%s/trips.csv' INTO TABLE Trips " +
			            "FIELDS TERMINATED BY ',' " +
			            "LINES TERMINATED BY '\\n' ";
            sql = String.format(sql, path);
            stmt.executeUpdate(sql);

            sql = "LOAD DATA LOCAL INFILE '%s/vehicles.csv' INTO TABLE Vehicles " +
			            "FIELDS TERMINATED BY ',' " +
			            "LINES TERMINATED BY '\\n' ";
            sql = String.format(sql, path);
            stmt.executeUpdate(sql);
            System.out.println("Processing...Data is loaded!");

        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("[ERROR] File Not Found");
        }
    }//1.3
    public static void CheckData() throws SQLException{

        try{
            Connection mySQLDB = connectToOracle();
	        Statement stmt = mySQLDB.createStatement();
            String sql = "SHOW TABLES";
            ResultSet resultSet = stmt.executeQuery(sql);
            if(!resultSet.isBeforeFirst()){
	            System.out.println("[ERROR] No records found.");
            }
            else{
                System.out.println("Number of records in each table:");
                int i = 0;
                sql = "SELECT * FROM Drivers";
                ResultSet resultDrivers = stmt.executeQuery(sql);
	            while(resultDrivers.next()){
                    i++;
	            }
                System.out.println("Drivers: " + i);
                i = 0;

                sql = "SELECT * FROM Vehicles";
                ResultSet resultVehicles = stmt.executeQuery(sql);
	            while(resultVehicles.next()){
                    i++;
	            }
                System.out.println("Vehicles: " + i);
                i = 0;

                sql = "SELECT * FROM Passengers";
                ResultSet resultPassengers = stmt.executeQuery(sql);
	            while(resultPassengers.next()){
                    i++;
	            }
                System.out.println("Passengers: " + i);
                i = 0;

                sql = "SELECT * FROM Requests";
                ResultSet resultRequests = stmt.executeQuery(sql);
	            while(resultRequests.next()){
                    i++;
	            }
                System.out.println("Requests: " + i);
                i = 0;

                sql = "SELECT * FROM Trips";
                ResultSet resultTrips = stmt.executeQuery(sql);
	            while(resultTrips.next()){
                    i++;
	            }
                System.out.println("Trips: " + i);
            }
        }
        catch(Exception e){
            System.out.println("[ERROR] No available data");
        }
    }//1.4

    private static boolean CheckID(int ID)throws SQLException{

        //SQL
        //Check if the input ID exists
        Connection mySQLDB = connectToOracle();
        String Passenger = "SELECT * FROM Passengers P WHERE P.PassengerID = %d";
        Passenger = String.format(Passenger, ID);
        Statement stmt = mySQLDB.createStatement();
        ResultSet resultSet = stmt.executeQuery(Passenger);
        if(!resultSet.isBeforeFirst()){
            return false;
        }else{
            return true;
        }
    }
    private static boolean CheckTripID(int tID, int ID)throws SQLException{

        boolean i = false;

        //SQL
        //Check if the input trip ID exists
        Connection mySQLDB = connectToOracle();
        String trip = "SELECT * FROM Trips WHERE TripID = %d AND PassengerID = %d AND Rating =0 AND End IS NOT NULL";
        trip = String.format(trip, tID, ID);
        Statement stmt = mySQLDB.createStatement();
        ResultSet tripRecord = stmt.executeQuery(trip);
        if(tripRecord.next())
            i = true;

        if(i){
            return true;
        }
        else{
            System.out.println("[ERROR] Invalid Trip ID");
            return false;
        }
    }
    private static boolean CheckDriverID(int ID)throws SQLException{

        boolean i = false;

        //SQL
        //check if the input ID exists
        Connection mySQLDB = connectToOracle();
        String Driver = "SELECT * FROM Drivers D WHERE D.DriverID = %d";
        Driver = String.format(Driver, ID);
        Statement stmt = mySQLDB.createStatement();
        ResultSet driverRecord = stmt.executeQuery(Driver);
        if(driverRecord.next())
            i = true;

        if(i){
            return true;
        }
        else{
            System.out.println("[ERROR] Invalid Driver ID");
            return false;
        }
    }

    public static void RequestRide() throws SQLException{
        int ID = 0;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter your ID.");
        ID = keyboard.nextInt();
        if (keyboard.nextLine() != "\n") {
        }
        System.out.println("Please enter the number of passengers.");
        int pNumber = keyboard.nextInt();
        if (keyboard.nextLine() != "\n") {
        }
        System.out.println("Please enter the earliest model year.(Press enter to skip)");
        int modelYear = 0;
        String k = keyboard.nextLine();

        if(k.length()==4){
            int t=0,h=0,d=0,m=0;
            t = (int)k.charAt(0) - 48;
            h = (int)k.charAt(1) - 48;
            d = (int)k.charAt(2) - 48;
            m = (int)k.charAt(3) - 48;
            if(t>=0 && t<=9 && h>=0 && h<=9 && d>=0 && d<=9 && m>=0 && m<=9){
                modelYear = t*1000+h*100+d*10+m;
            }else{
                modelYear = 1;
            }
        }else{
            modelYear = 0;
        }
        String model = "";
        System.out.println("Please enter the model.(Press enter to skip)");
        model = keyboard.nextLine();
        //JUDGE
        if(CheckID(ID) == false){
            System.out.println("[ERROR] Passenger not found.");
        }else if(pNumber < 1 || pNumber > 8){
            System.out.println("[ERROR] Number of passengers is out of bound.");
        }else if(modelYear != 0 && (modelYear < 2010 || modelYear > 2018) ){
            System.out.println("[ERROR] The order of earlist model year is out of bound.");
        }else{
         //SQL
            Connection mySQLDB = connectToOracle();
            PreparedStatement pstmt =
            mySQLDB.prepareStatement( "INSERT INTO Requests (PassengerID, ModelYear, Model, SeatOrder, Taken) values (?,?,?,?,?)" );
            //pstmt.setInt ( 1, reuqestID );
            pstmt.setInt ( 1, ID );
            pstmt.setInt ( 2, modelYear );
            pstmt.setString ( 3, model );
            pstmt.setInt ( 4, pNumber );
            pstmt.setBoolean ( 5, false );
            pstmt.executeUpdate();
            //Generate the Request

            int ableDriverNumber = 0;
            //SQL
            //Check the Drivers able to take the request
            model = model.toLowerCase();
            String smodelYear = String.valueOf(modelYear);
            String spNumber = String.valueOf(pNumber);
            PreparedStatement sql = mySQLDB.prepareStatement("SELECT * FROM Vehicles V WHERE LOWER(V.Model) LIKE LOWER(CONCAT('%', ?, '%')) AND V.ModelYear >= ? AND V.Seats >= ?");
            sql.setString ( 1, model );
            sql.setInt ( 2, modelYear );
            sql.setInt ( 3, pNumber );
            /*String sql = "SELECT * " +
                        "FROM Vehicles V " +
                        "WHERE LOWER(V.Model) LIKE '%%s%' AND V.ModelYear >= '%s' AND V.Seats >= '%s'";
            sql = String.format(sql, model, smodelYear, spNumber);
            Statement stmt = mySQLDB.createStatement(sql);*/
            ResultSet ableDrivers = sql.executeQuery();

            while(ableDrivers.next()){
                ableDriverNumber ++;
            }
            System.out.println("Your request is placed. "+ ableDriverNumber +" drivers are able to take the request");

        }

    }//2.1
    public static void CheckRecords()throws SQLException{
        System.out.println("Please enter your ID:");
        int ID = 0;
        Scanner keyboard = new Scanner(System.in);
        ID = keyboard.nextInt();
        if (keyboard.nextLine() != "\n") {
        }
        System.out.println("Please enter the start date.");
        String startDate = keyboard.nextLine();
        System.out.println("Please enter the end date.");
        String endDate = keyboard.nextLine();

        //SQL
        //Return the records
        Connection mySQLDB = connectToOracle();
        if(CheckID(ID) == false){
            System.out.println("[ERROR] Passenger not found.");
        }else{
            String trip =
                "SELECT * FROM Trips T, Drivers D, Vehicles V "+
                "WHERE T.PassengerID = %d AND T.DriverID = D.DriverID AND D.VehicleID = V.VehicleID AND Date(T.Start) between '%s' and '%s' " +
                "ORDER BY T.Start DESC";
            trip = String.format(trip, ID, startDate, endDate);
            Statement stmt = mySQLDB.createStatement();
            ResultSet tripRecord = stmt.executeQuery(trip);

            if(!tripRecord.isBeforeFirst()){
                System.out.println("[ERROR] No records found");
            }
            else{
                System.out.println("Trip ID, Driver Name, Vehicle ID, Vehicle Model, Start, End, Fee, Rating");
                while(tripRecord.next()){
                    System.out.print(tripRecord.getInt("TripID")+", ");
                    System.out.print(tripRecord.getString("DriverName")+", ");
                    System.out.print(tripRecord.getString("VehicleID")+", ");
                    System.out.print(tripRecord.getString("Model")+", ");
                    System.out.print(tripRecord.getString("Start")+", ");
                    System.out.print(tripRecord.getString("End")+", ");
                    System.out.print(tripRecord.getInt("Fee")+", ");
                    System.out.println(tripRecord.getInt("Rating"));
                }
            }
        }



    }//2.2

    public static void RateTrip()throws SQLException{
        System.out.println("Please enter your ID:");
        int ID = 0;
        Scanner keyboard = new Scanner(System.in);
        ID = keyboard.nextInt();
        if (keyboard.nextLine() != "\n") {
        }
        //do{
        //    ID = keyboard.nextInt();
        //}while(!CheckID(ID));
        System.out.println("Please enter the trip ID:");
        int tripID = 0;
        tripID = keyboard.nextInt();
        if (keyboard.nextLine() != "\n") {
        }
        //do{
        //    tripID = keyboard.nextInt();
        //    if (keyboard.nextLine() != "\n") {
        //}
        //}while(!CheckTripID(tripID));
        System.out.println("Please enter the rating:");
        int rating = 0;
        rating = keyboard.nextInt();
        if (keyboard.nextLine() != "\n") {
        }
        //do{
        //    rating = keyboard.nextInt();
        //    if (keyboard.nextLine() != "\n") {
        //}
        //    if(rating != 0 && rating != 1 && rating != 2 && rating != 3 && rating != 4 && rating != 5){
        //        System.out.println("[ERROR] Please enter a integer between 0-5");
        //    }
        //}while(!CheckTripID(tripID));


        //SQL
        //rate the trip

        if(CheckID(ID) == false){
            System.out.println("[ERROR] Passenger not found.");
        }else if(CheckTripID(tripID, ID) == false){
            //System.out.println("[ERROR] Trips not found.");
        }else if(rating != 1 && rating != 2 && rating != 3 && rating != 4 && rating != 5){
            System.out.println("[ERROR] Please enter a integer between 0-5.");
        }else{
            Connection mySQLDB = connectToOracle();
            String Rating = "UPDATE Trips SET Rating = %d WHERE TripID = %d";
            Rating = String.format(Rating, rating, tripID);
            Statement stmt = mySQLDB.createStatement();
            stmt.executeUpdate(Rating);

            //Display the rated trip

            String TripR = "SELECT * "+
                        "FROM Trips T , Drivers D, Vehicles V "+
                        "WHERE T.TripID = %d AND T.DriverID = D.DriverID AND D.VehicleID = V.VehicleID";
            TripR = String.format(TripR, tripID);
            stmt = mySQLDB.createStatement();
            ResultSet ratedTrip = stmt.executeQuery(TripR);

            System.out.println("Trip ID, Driver Name, Vehicle ID, Vehicle Model, Start, End, Fee, Rating");
            while(ratedTrip.next()){
                System.out.print(ratedTrip.getInt("TripID")+", ");
                System.out.print(ratedTrip.getString("DriverName")+", ");
                System.out.print(ratedTrip.getString("VehicleID")+", ");
                System.out.print(ratedTrip.getString("Model")+", ");
                System.out.print(ratedTrip.getDate("Start")+", ");
                System.out.print(ratedTrip.getDate("End")+", ");
                System.out.print(ratedTrip.getInt("Fee")+", ");
                System.out.println(ratedTrip.getInt("Rating"));
            }
        }



    }//2.3

    public static void TakeRequest()throws SQLException{
        System.out.println("Please enter your ID:");
        int ID = 0;
        Scanner keyboard = new Scanner(System.in);
        do{
            ID = keyboard.nextInt();
            if (keyboard.nextLine() != "\n") {
            }
        }while(!CheckDriverID(ID));
        Connection mySQLDB = connectToOracle();
        Statement stmt = mySQLDB.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        String sql="SELECT * FROM Trips T WHERE T.DriverID = %d ";
        sql = String.format(sql, ID);
        ResultSet checkFree = stmt.executeQuery(sql);
        while(checkFree.next()){
            //System.out.println(checkFree.getTimestamp("Start"));
            //System.out.println(checkFree.getTimestamp("End"));
            if(checkFree.getTimestamp("End")==null){
                System.out.println("You have an unfinished trip :)");
                return;
            }
        }

        System.out.println("Request ID, Passenger name, Passengers");

        //SQL
        //Display all able requests

        /*String AbleRequests =
            "SELECT R.RequestID,P.PassengerName,R.SeatOrder FROM Requests R,Drivers D,Vehicles V,Passengers P"+
            "WHERE D.VehicleID = V.VehicleID AND P.PassengerID = R.PassengerID AND "+
            "V.Model LIKE CONCAT('%', R.Model, '%') AND V.ModelYear >= R.ModelYear AND V.Seats >= R.SeatOrder AND "+
            "R.Taken = false AND D.DriverID = %d";*/
        String AbleRequests =
                "SELECT R.RequestID,P.PassengerName,R.SeatOrder FROM Requests R,Drivers D,Vehicles V,Passengers P "+
                "WHERE D.VehicleID = V.VehicleID AND P.PassengerID = R.PassengerID AND "+
                "V.Model LIKE CONCAT('%', R.Model, '%') AND ";

        String temp = "V.ModelYear >= R.ModelYear AND V.Seats >= R.SeatOrder AND R.Taken = false AND D.DriverID = %d";
        temp = String.format(temp, ID);
        AbleRequests = AbleRequests.concat(temp);
        //System.out.println(AbleRequests);

        ResultSet ableRequests = stmt.executeQuery(AbleRequests);
        int requestID = 0;
        String pName = "";
        String pNumber = "";

        if(!ableRequests.isBeforeFirst()){
            System.out.println("[ERROR] Not exists able request");
            return;
        }
        else{
            while(ableRequests.next()){
                requestID = ableRequests.getInt("RequestID");
                pName = ableRequests.getString("PassengerName");
                pNumber = ableRequests.getString("SeatOrder");
                System.out.println(requestID+", "+pName+", "+pNumber);
            }
        }

        System.out.println("Please enter the request ID");
        int rID = 0;
        boolean able = true;
        do{
            int l = 0;
            rID = keyboard.nextInt();
            if (keyboard.nextLine() != "\n") {
            }
            int RequestID = 0;
            ableRequests.beforeFirst();
            while(ableRequests.next()){
                RequestID = ableRequests.getInt("RequestID");
                //  System.out.println(RequestID);
                if(RequestID == rID)
                    l=1;
            }
            if(l==1){
                break;
            }
            System.out.println("[ERROR] Invalid Request ID");
        }while(able);
        int tripID = 0;


        /*stmt = mySQLDB.createStatement();
        ResultSet allTrip = stmt.executeQuery("SELECT COUNT(*) FROM Trips");
        tripID = allTrip.getInt(1)+1;*/
        String thisr =
            "SELECT P.PassengerID,P.PassengerName FROM Passengers P, Requests R WHERE R.RequestID = %d AND R.PassengerID = P.PassengerID";
        thisr = String.format(thisr, rID);
        ResultSet theRequest = stmt.executeQuery(thisr);
        int pID = 0;
        while(theRequest.next()){
            pID = theRequest.getInt(1);
            pName =theRequest.getString(2);
        }
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tripStartTime = dateFormat.format(now);
        //Mark the request taken and generate a new trip
        java.sql.Timestamp sqlStartDate = new java.sql.Timestamp(now.getTime());
        String Take = "UPDATE Requests SET Taken = true WHERE RequestID=%d";
        Take = String.format(Take, rID);
        stmt = mySQLDB.createStatement();
        stmt.executeUpdate(Take);

        PreparedStatement pstmt =
        mySQLDB.prepareStatement( "insert into Trips (DriverID,PassengerID,Start,Fee,Rating) values (?,?,?,?,?)" );
        //pstmt.setInt ( 1, tripID );
        pstmt.setInt ( 1, ID );
        pstmt.setInt ( 2, pID );
        pstmt.setTimestamp ( 3, sqlStartDate );
        pstmt.setInt ( 4, 0 );
        pstmt.setInt ( 5, 0 );
        pstmt.execute();
        String thisT =
            "SELECT T.TripID FROM Trips T WHERE T.DriverID = %d AND T.Start = '%s'";
        thisT = String.format(thisT, ID, tripStartTime);
        ResultSet theTrip = stmt.executeQuery(thisT);
        while(theTrip.next()){
            tripID = theTrip.getInt(1);
        }
        System.out.println("Trip ID, Passenger name, Start time");
        System.out.println(tripID+", "+pName+", "+tripStartTime);

    }//3.1
    public static void FinishTrip() throws SQLException{
        System.out.println("Please enter your ID:");
        int ID = 0;
        Scanner keyboard = new Scanner(System.in);
        do{
            ID = keyboard.nextInt();
            if (keyboard.nextLine() != "\n") {
            }
        }while(!CheckDriverID(ID));
        int tripID = 0;
        int pID = 0;
        String pName = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String st = "1970-01-01 00:00:00";
        String ed = "";
        try{
            Date startTime = dateFormat.parse(st);
            //SQL
            //Get the trip's data
            Connection mySQLDB = connectToOracle();
            String Trip = "SELECT T.*,P.PassengerName FROM Trips T,Passengers P WHERE T.PassengerID = P.PassengerID AND T.DriverID = %d AND T.End IS NULL";
            Trip = String.format(Trip, ID);
            Statement stmt = mySQLDB.createStatement();
            ResultSet thisTrip = stmt.executeQuery(Trip);

            if(!thisTrip.isBeforeFirst()){
                System.out.println("[ERROR] You don't have a current trip");
                return;
            }
            else{
                System.out.println("Trip ID, Passenger ID, Start time");
                while(thisTrip.next()){
                    tripID = thisTrip.getInt("TripID");
                    pID= thisTrip.getInt("PassengerID");
                    st = thisTrip.getString("Start");
                    pName = thisTrip.getString("PassengerName");
                    System.out.println(tripID+", "+pID+", "+st);
                }
            }
            String k = "";
            char z = ' ';
            while(z!='y'&&z!='n'){
                System.out.println("Do you want to finish the trip? [y/n]");
                k = keyboard.nextLine();
                z = k.charAt(0);
                if(z!='y'&&z!='n')
                    System.out.println("[ERROR] Please enter y or n");
            }
            if(z=='y'){

                Date endTime = new Date();
                startTime = dateFormat.parse(st);

                int fee = 0;
                try{
                    long ms = endTime.getTime()-startTime.getTime();
                    long min = ms / (1000*60);
                    fee = (int)min;
                }
                catch(Exception e){
                    System.out.println("[ERROR] Wrong time information");
                }
                String EndTime = dateFormat.format(endTime);;
                //SQL
                //Update the trip's information
                Connection conn = connectToOracle();
                String End = "UPDATE Trips SET End = NOW() WHERE TripID=%d";
                End = String.format(End, tripID);
                stmt = conn.createStatement();
                stmt.executeUpdate(End);


                String Fee = "UPDATE Trips SET Fee = %d WHERE TripID=%d";
                Fee = String.format(Fee, fee, tripID);
                stmt = conn.createStatement();
                stmt.executeUpdate(Fee);

                //Get the undated trip data

                Trip = "SELECT T.* FROM Trips T WHERE T.DriverID = %d AND T.TripID = %d";
                Trip = String.format(Trip, ID, tripID);
                stmt = mySQLDB.createStatement();
                ResultSet thisTripU = stmt.executeQuery(Trip);
                if(!thisTripU.isBeforeFirst()){
                    System.out.println("[ERROR] You don't have a current trip");
                    return;
                }
                else{
                    System.out.println("Trip ID, Passenger name, Start time, End time, Fee");
                    while(thisTripU.next()){
                        tripID = thisTripU.getInt("TripID");
                        st = thisTripU.getString("Start");
                        ed = thisTripU.getString("End");
                        fee = thisTripU.getInt("Fee");
                        System.out.println(tripID+", "+pName+", "+st+", "+ed+", "+fee);
                    }

                }
            }
            else{
                System.out.println("You give up finishing the trip");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }//3.2
    public static void CheckRating()throws SQLException{
        System.out.println("Please enter your ID:");
        int ID = 0;
        Scanner keyboard = new Scanner(System.in);
        do{
            ID = keyboard.nextInt();
            if (keyboard.nextLine() != "\n") {
            }
        }while(!CheckDriverID(ID));
        double dRating = 0;
        //SQL
        //Get rating
        Connection mySQLDB = connectToOracle();
        String Driver = "SELECT T.Rating FROM Trips T WHERE T.DriverID = %d ORDER BY T.Start DESC";
        Driver = String.format(Driver, ID);
        Statement stmt = mySQLDB.createStatement();
        ResultSet RateRecord = stmt.executeQuery(Driver);
        int rateTimes = 0;
        int totalRate = 0;
        int k;
        while(RateRecord.next() && rateTimes < 5){
            k = RateRecord.getInt("Rating");
            if(k>=1 && k<=5){
                rateTimes ++;
                totalRate += k;
            }
        }
        if(rateTimes < 5){
            System.out.println("Your rating is not yet determined.");
        }else{
            dRating = (double)totalRate/(double)rateTimes;
            DecimalFormat df = new DecimalFormat("0.00");
            System.out.println("Your driver rating is "+df.format(dRating)+'.');
        }

    }//3.3
    public static int Login(){
        System.out.println("Welcome! Who are you?");
        System.out.println("1. An Administrator");
        System.out.println("2. A Passenger");
        System.out.println("3. A Driver");
        System.out.println("4. None of the above");
        Scanner keyboard = new Scanner(System.in);
        int identity = 0;
        while(identity<1 || identity>4 ){
            System.out.println("Please enter [1-4].");
            identity = keyboard.nextInt();
            if (keyboard.nextLine() != "\n") {
            }
            if(identity<1 || identity>4 )
                System.out.println("[ERROR] Invalid input.");
        }
        return identity;
    }//Start UI

    public static int Administrator(){
        String mission = "";
        System.out.println("Administrator, what would you like to do?");
        System.out.println("1. Create tables");
        System.out.println("2. Delete tables");
        System.out.println("3. Load data");
        System.out.println("4. Check data");
        System.out.println("5. Go back");
        Scanner keyboard = new Scanner(System.in);
        char z = ' ';
        while(z<49 || z>53 || mission.length()>1){
            System.out.println("Please enter [1-5].");
            mission = keyboard.nextLine();
            z = mission.charAt(0);
            if(z<49 || z>53 || mission.length()>1)
                System.out.println("[ERROR] Invalid input.");
        }
        return (int)z - 48;
    }//1
    public static int Passenger(){
        String mission = "";
        System.out.println("Passenger, what would you like to do?");
        System.out.println("1. Request a ride");
        System.out.println("2. Check trip records");
        System.out.println("3. Rate a trip");
        System.out.println("4. Go back");
        Scanner keyboard = new Scanner(System.in);
        char z = ' ';
        while(z<49 || z>52 || mission.length()>1){
            System.out.println("Please enter [1-4].");
            mission = keyboard.nextLine();
            z = mission.charAt(0);
            if(z<49 || z>52 || mission.length()>1)
                System.out.println("[ERROR] Invalid input.");
        }
        return (int)z - 48;
    }//2
    public static int Driver(){
        String mission = "";
        System.out.println("Driver, what would you like to do?");
        System.out.println("1. Take a request");
        System.out.println("2. Finish a trip");
        System.out.println("3. Check driver rating");
        System.out.println("4. Go back");
        Scanner keyboard = new Scanner(System.in);
        char z = ' ';
        while(z<49 || z>52 || mission.length()>1){
            System.out.println("Please enter [1-4].");
            mission = keyboard.nextLine();
            z = mission.charAt(0);
            if(z<49 || z>52 || mission.length()>1)
                System.out.println("[ERROR] Invalid input.");
        }
        return (int)z - 48;
    }//3

    public static void main(String[] args) {

        try{
            while(true){
                int identity = Login();
                int mission = 0;
                if (identity == 1){
                    int a=0;
                    //Administrator admin = new Administrator();
                    while(a==0){
                        mission = Administrator();
                        switch (mission){
                            case 1:
                                CreateTable();break;
                            case 2:
                                DeleteTable();break;
                            case 3:
                                LoadData();break;
                            case 4:
                                CheckData();break;
                            case 5:
                                a=1;break;
                        }
                    }
                }
                if (identity == 2){
                    int p=0;
                    while(p==0){
                        mission = Passenger();
                        switch (mission){
                            case 1:
                                RequestRide();break;
                            case 2:
                                CheckRecords();break;
                            case 3:
                                RateTrip();break;
                            case 4:
                                p=1;break;
                        }
                    }

                }
                if (identity == 3){
                    int d=0;
                    while(d==0){
                        mission = Driver();
                        switch (mission){
                            case 1:
                                TakeRequest();break;
                            case 2:
                                FinishTrip();break;
                            case 3:
                                CheckRating();break;
                            case 4:
                                d=1;break;
                        }
                    }
                }
                if(identity == 4){
                    break;
                }
            }

	    } catch (SQLException e){
            System.out.println("error occurred\n");
            System.out.println(e);
	    }
    }
}
