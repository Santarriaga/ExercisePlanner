package com.grumpy.exerciseplanner.DatabaseFiles;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;

@androidx.room.Database(entities = {Exercise.class, Plan.class}, version = 1, exportSchema = false)
public abstract class ExerciseDatabase extends RoomDatabase {

    public abstract ExerciseDao exerciseDao();
    public abstract PlanDao planDao();

    //singleton patter
    private static ExerciseDatabase instance;

    public static synchronized ExerciseDatabase getInstance(Context context){
        if(null == instance){
            instance = Room.databaseBuilder(context, ExerciseDatabase.class,"exercise_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(initialCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback initialCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            populateInitialData(instance);
        }
    };



    private static void populateInitialData(ExerciseDatabase db) {
        HandlerThread ht = new HandlerThread("MyHandlerThread");
        ht.start();

        Handler handler = new Handler(ht.getLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ExerciseDao exerciseDao = db.exerciseDao();

                ArrayList<Exercise>trainings = new ArrayList<>();

                Exercise benchPress = new Exercise("Bench Press", " an upper-body weight training exercise in which the trainee presses a weight upwards while lying on a weight training bench.",
                        "The bench press is an upper-body weight training exercise in which the trainee presses a weight upwards" +
                                " while lying on a weight training bench. The exercise uses the pectoralis major, the anterior deltoids, and the " +
                                "triceps, among other stabilizing muscles. A barbell is generally used to hold the weight, but a pair of dumbbells can also be used",
                        "ic_weightlifting",
                        "https://images.unsplash.com/photo-1532029837206-abbe2b7620e3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1500&q=80");
                trainings.add(benchPress);

                Exercise yoga = new Exercise("Yoga","Yoga (/ˈjoʊɡə/;[1] Sanskrit: योग; About this soundpronunciation) is a group of physical, mental, and spiritual practices or disciplines which originated in ancient India.",
                        "Yoga (/ˈjoʊɡə/;[1] Sanskrit: योग; About this soundpronunciation) is a group of physical, mental, and spiritual practices or " +
                                "disciplines which originated in ancient India. Yoga is one of the six Āstika (orthodox) schools of Hindu philosophical traditions.",
                        "ic_meditation",
                        "https://images.unsplash.com/photo-1575052814086-f385e2e2ad1b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1500&q=80");
                trainings.add(yoga);

                Exercise legPressMachine = new Exercise("Leg Press", "a compound weight training exercise in which the individual pushes a weight or resistance away from them using their legs.",
                        "The leg press is a compound weight training exercise in which the individual pushes a weight or resistance away from them using" +
                                " their legs. The term leg press machine refers to the apparatus used to perform this exercise.[1] The leg press can be used to evaluate an" +
                                " athlete's overall lower body strength (from knee joint to hip). It can help to build squat strength.[2] If performed correctly, the inclined " +
                                "leg press can help develop knees to manage heavier free weights,[3] on the other hand, it has the potential to inflict grave injury: the knees " +
                                "could bend the wrong way if they are locked during the exercise.[4]",
                        "ic_machines",
                        "https://images.unsplash.com/photo-1434682772747-f16d3ea162c3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1506&q=80");
                trainings.add(legPressMachine);

                Exercise basketball = new Exercise("BasketBall","Basketball, colloquially referred to as hoops,[1] is a team sport in which two teams, most commonly of five players each",
                        "Basketball, colloquially referred to as hoops,[1] is a team sport in which two teams, most commonly of five players each, opposing" +
                                " one another on a rectangular court, compete with the primary objective of shooting a basketball (approximately 9.4 inches (24 cm) in " +
                                "diameter) through the defender's hoop (a basket 18 inches (46 cm) in diameter mounted 10 feet (3.048 m) high to a backboard at each end of" +
                                " the court) while preventing the opposing team from shooting through their own hoop.",
                        "ic_sports",
                        "https://images.unsplash.com/photo-1553108715-308e8537ce55?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1600&q=80");
                trainings.add(basketball);

                Exercise pushUp = new Exercise("Push Up", "A common calisthenics exercise beginning from the prone position.",
                        "A push-up (or press-up in British English) is a common calisthenics exercise beginning from the prone position. " +
                                "By raising and lowering the body using the arms, push-ups exercise the pectoral muscles, triceps, and anterior deltoids," +
                                " with ancillary benefits to the rest of the deltoids, serratus anterior, coracobrachialis and the midsection as a whole. " +
                                "Push-ups are a basic exercise used in civilian athletic training or physical education and commonly in military physical training." +
                                " They are also a common form of punishment used in the military, school sport, and some martial arts disciplines.",
                        "ic_arm",
                        "https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1500&q=80");
                trainings.add(pushUp);

                Exercise sitUp = new Exercise("Sit Up", " is an abdominal endurance training exercise to strengthen, tighten and tone the abdominal muscles",
                        "The sit-up (or curl-up) is an abdominal endurance training exercise to strengthen, tighten and tone the " +
                                "abdominal muscles. It is similar to a crunch (crunches target the rectus abdominis and also work the biceps and external " +
                                "and internal obliques), but sit-ups have a fuller range of motion and condition additional muscles.",
                        "ic_calisthenics",
                        "https://images.pexels.com/photos/3076516/pexels-photo-3076516.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
                trainings.add(sitUp);

                Exercise weights = new Exercise("Weigh Lifting", "Weight training is a common type of strength training for developing the strength and size of skeletal muscles.",
                        "Weight training is a common type of strength training for developing the strength and size of skeletal muscles." +
                                " It utilizes the force of gravity in the form of weighted bars, dumbbells or weight stacks in order to oppose the force" +
                                " generated by muscle through concentric or eccentric contraction. Weight training uses a variety of specialized equipment to target" +
                                " specific muscle groups and types of movement.",
                        "ic_weightlifting",
                        "https://images.pexels.com/photos/791763/pexels-photo-791763.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
                trainings.add(weights);

                Exercise squat = new Exercise("Squat", "A squat is a strength exercise in which the trainee lowers their hips from a standing position and then stands back up.",
                        "A squat is a strength exercise in which the trainee lowers their hips from a standing position and then stands back up. " +
                                "During the descent of a squat, the hip and knee joints flex while the ankle joint dorsiflexes; conversely the hip and knee joints" +
                                " extend and the ankle joint plantarflexes when standing up.",
                        "ic_calisthenics",
                        "https://images.pexels.com/photos/2417485/pexels-photo-2417485.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
                trainings.add(squat);

                Exercise pullUp = new Exercise("Pull Up", "A pull-up is an upper-body strength exercise.",
                        "A pull-up is an upper-body strength exercise. The pull-up is a closed-chain movement where " +
                                "the body is suspended by the hands and pulls up. As this happens, the elbows flex and the shoulders adduct " +
                                "and extend to bring the elbows to the torso.",
                        "ic_calisthenics",
                        "https://images.pexels.com/photos/4608157/pexels-photo-4608157.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
                trainings.add(pullUp);

                Exercise soccer = new Exercise("Soccer", "Association football, more commonly known as football or soccer,[a] is a team sport played with a spherical ball between two teams of 11 players.",
                        "Association football, more commonly known as football or soccer,[a] is a team sport played with a spherical ball between two teams of 11 players. " +
                                "It is played by approximately 250 million players in over 200 countries and dependencies, making it the world's most popular sport. The game is played on a" +
                                " rectangular field called a pitch with a goal at each end. The object of the game is to outscore the opposition by moving the ball beyond the goal line into " +
                                "the opposing goal. The team with the higher number of goals wins the game.",
                        "ic_sports","https://images.unsplash.com/photo-1560272564-c83b66b1ad12?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=687&q=80");
                trainings.add(soccer);

                Exercise meditation = new Exercise("Meditation","Meditation is a practice where an individual uses a technique – such as mindfulness, or focusing the mind on a particular object,",
                        "Meditation is a practice where an individual uses a technique – such as mindfulness, or focusing the mind on a particular object, " +
                                "thought, or activity – to train attention and awareness, and achieve a mentally clear and emotionally calm and stable state.Meditation is " +
                                "practiced in numerous religious traditions. The earliest records of meditation (dhyana) are found in the Vedas, and meditation exerts a " +
                                "salient role in the contemplative repertoire of Hinduism and Buddhism.[7] Since the 19th century, Asian meditative techniques have spread" +
                                " to other cultures where they have also found application in non-spiritual contexts, such as business and health",
                        "ic_meditation",
                        "https://images.unsplash.com/photo-1506126613408-eca07ce68773?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=862&q=80");
                trainings.add(meditation);

                Exercise jumpRope = new Exercise("Jump Rope", "is a tool used in the sport of skipping/jump rope where one or more participants jump over a rope swung so that it passes under their feet and over their heads",
                        "A skipping rope (British English) or jump rope (American English) is a tool used in the sport of skipping/jump rope where one or" +
                                " more participants jump over a rope swung so that it passes under their feet and over their heads. There are multiple subsets of " +
                                "skipping/jump rope, including single freestyle, single speed, pairs, three-person speed (Double Dutch), and three-person freestyle" +
                                " (Double Dutch freestyle).",
                        "ic_cardio",
                        "https://images.unsplash.com/photo-1514994667787-b48ca37155f0?ixlib=rb-1.2.1&auto=format&fit=crop&w=1518&q=80");
                trainings.add(jumpRope);

                Exercise jogging = new Exercise("Jogging", "a form of trotting or running at a slow or leisurely pace.",
                        "Jogging is a form of trotting or running at a slow or leisurely pace. The main intention is to increase physical " +
                                "fitness with less stress on the body than from faster running but more than walking, or to maintain a steady speed for longer " +
                                "periods of time. Performed over long distances, it is a form of aerobic endurance training.",
                        "ic_cardio",
                        "https://images.unsplash.com/photo-1559166631-ef208440c75a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80");
                trainings.add(jogging);

                Exercise bicepCurl = new Exercise("Biceps Culr", " general term for a series of strength exercises that involve brachioradialis, front deltoid and the main target on biceps brachii.",
                        "Biceps curl is a general term for a series of strength exercises that involve brachioradialis, front deltoid and the main target on biceps brachii.[1] " +
                                "Includes variations using barbell, dumbbell and resistance band, etc. The common point amongst them is the trainee lifting a certain amount " +
                                "of weight to contracting the biceps brachii, and tuck in their arms to the torso during the concentric phase. Once the biceps brachii is fully contracted, " +
                                "then return the weight to starting position during the eccentric phase.",
                        "ic_arm",
                        "https://images.unsplash.com/photo-1598267416826-c0d275951803?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=635&q=80");
                trainings.add(bicepCurl);





                for(Exercise e: trainings){
                    exerciseDao.insert(e);
                }
            }
        };
        handler.post(runnable);
    }


}
