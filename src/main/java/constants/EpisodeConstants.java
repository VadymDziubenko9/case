package constants;

import dto.Episode;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

@UtilityClass
@Data
public class EpisodeConstants {
    public static final Episode STRONG_HEALTH_SINGLE_EPISODE = Episode.builder().author("Alladin Fakir DO").type("Emergency Room").date("01/12/2020").time("05:08 pm").build();
    public static final Episode STRONG_HEALTH_SINGLE_EPISODE_NOTES = Episode.builder()
            .notes("""
                   StrongHealth
                   Left knee pain.  Left knee puncture laceration.
                   BACK NORMAL ROM.  NVI""")
            .build();


    public static final Episode BEST_SIDE_MEDICAL_GROUP_SINGLE_EPISODE = Episode.builder().author("Lawrence Kutner MD").type("MRI").date("01/17/2020").build();
    public static final Episode FISHING_SINGLE_EPISODE = Episode.builder().author("Claimant").type("Social Media").date("01/25/2020").build();
    public static final Episode CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_SINGLE_EPISODE = Episode.builder().author("Claimant").type("Intakea").date("01/30/2020").build();

    public static final Episode CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_SINGLE_EPISODE_NOTES = Episode.builder()
            .notes("""
                    MVA. Hit while driving a moped. Struck on right rear side.
                    ATTORNEY: Kerry Marena""")
            .build();

    public static final Episode CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_SINGLE_EPISODE_2 = Episode.builder().author("Eric Foreman DC").type("Chiropractic/Massage").date("01/30/2020").build();

    public static final Episode CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_SINGLE_EPISODE_NOTES_2 = Episode.builder()
            .notes("""
                    MVA 1/11/2020. Driver on a moped.  Fell to the ground, contacting his right shoulder, and rolling to his left knee.
                    Neck pain 5. Upper back 4-8. Low back 4. Left leg 4-5.  SPRAIN RIGHT SHOULDER.
                    Neuro intact.""")
            .build();

    public static final Episode JOHN_SMITH_EPISODE = Episode.builder().author("Claimant").date("03/28/2022").build();
    public static final Episode JOHN_SMITH_EPISODE_NOTES = Episode.builder()
            .notes("""
                    Pg 28: Does 15 minutes ab routine.  Sometimes 20 something minutes.
                    Pg 42, had minor invasive mid-back surgery in November of 2021.
                    Pg 54: Has had RFA performed to the neck twice.
                    Pg 55: Relief of mid back pain from RFA for 3-4 months.
                    Pg 89: Has worked 50 hours a week for the past 4 months""")
            .build();

    public static final Episode STRONG_HEALTH_EPISODE = Episode.builder().author("Emergency Room Provider").type("Emergency Room").date("01/12/2020").time("02:39 pm").build();
    public static final Episode STRONG_HEALTH_EPISODE_NOTES = Episode.builder().notes("c/o left knee lac from last night.  Seen last nite as well.").build();
    public static final Episode STRONG_HEALTH_EPISODE_2 = Episode.builder().author("Buddy Bomber DO").type("Emergency Room").date("01/11/2020").time("11:59 pm").build();

    public static final Episode STRONG_HEALTH_EPISODE_NOTES_2 = Episode.builder()
            .notes("""
                    StrongHealth Carrollwood:
                    Laceration to right forearm. Right tibia pain.
                    NO INJURY to HEAD, CERVICAL SPINE, CHEST or ABDOMEN.
                    Motor and sensory intact.
                    Laceration repair.""")
            .build();

    public static final Episode STRONG_HEALTH_EPISODE_3 = Episode.builder().author("Fernando Wargas MD").type("X-Ray").date("01/12/2020").build();
    public static final Episode STRONG_HEALTH_EPISODE_NOTES_3 = Episode.builder().notes("Right Tib/Fib: Soft tissue swelling laterally at the ankle.").build();
    public static final Episode STRONG_HEALTH_EPISODE_4 = Episode.builder().author("Alladin Fakir DO").type("Emergency Room").date("01/12/2020").time("05:08 pm").build();
    public static final Episode STRONG_HEALTH_EPISODE_NOTES_4 = Episode.builder()
            .notes("""
                    StrongHealth
                    Left knee pain.  Left knee puncture laceration.
                    BACK NORMAL ROM.  NVI""")
            .build();

    public static final Episode ROBERT_CHASE_EPISODE = Episode.builder().date("03/31/2022").build();

    public static final Episode ROBERT_CHASE_EPISODE_NOTES = Episode.builder()
            .notes("""
                    From attorney
                    Page 49, "MILD PAIN. WOULD YOU AGREE THAT MILD PAIN ON EXTENSION AND MILD TENDERNESS ON PALPATION IS INCONSISTENT WITH 8 OUT OF 10 SEVERE PAIN? YES"
                    Page 73, "IN RETROSPECT, WE SHOULD HAVE AT LEAST CAPTURED THAT INITIAL" REFERENCE TO A NEW THORACIC PAIN.
                    Page 78, AGREES THAT THE THORACIC PAIN WASN'T PRESENT OR REPORTED FOR SEVERAL MONTHS AFTER THE ACCIDENT.
                    Page 86 "WHETHER OR NOT THEY WERE THERE OR THEY WERE AGGRAVATED BY THE ACCIDENT, THAT'S, I CANNOT SAY, YOU KNOW, I CANNOT SAY WHETHER OR NOT THEY WERE THERE.""")
            .build();

    public static final Episode PAIN_EXPERTS_MR_EPISODE = Episode.builder().type("Operation").date("08/27/2021").build();
    public static final Episode PAIN_EXPERTS_MR_EPISODE_NOTES = Episode.builder()
            .notes("""
                    T7-8 and T8-9 left laminoforaminotomy
                    Stem Cell injection""")
            .build();

    public static final Episode PAIN_EXPERTS_MR_EPISODE_2 = Episode.builder().author("Christopher Jacobson MD").type("Physiatry").date("08/31/2021").build();
    public static final Episode PAIN_EXPERTS_MR_EPISODE_NOTES_2 = Episode.builder()
            .notes("""
                    Removal of drain.
                    Status post thoracic laminotomy and foraminotomy.
                    No lifting more than 10-15 pounds.
                    Continued Percocet and Flexeril.""")
            .build();

    public static final Episode PAIN_EXPERTS_MR_EPISODE_3 = Episode.builder().type("Orthopaedic").date("09/09/2021").build();
    public static final Episode PAIN_EXPERTS_MR_EPISODE_NOTES_3 = Episode.builder().notes("Postop from thoracic laminotomy. Pain, 5").build();
    public static final Episode PAIN_EXPERTS_MR_EPISODE_4 = Episode.builder().type("Orthopaedic").date("10/07/2021").build();
    public static final Episode PAIN_EXPERTS_MR_EPISODE_NOTES_4 = Episode.builder().notes("Postop.Pain does not radiate. No restrictions at this point. Activities as tolerated.").build();
    public static final Episode PAIN_EXPERTS_MR_EPISODE_5 = Episode.builder().author("Kira Nightley PA").type("Medical").date("08/23/2021").build();
    public static final Episode PAIN_EXPERTS_MR_EPISODE_NOTES_5 = Episode.builder().notes("Preoperative evaluation.").build();
    public static final Episode CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE = Episode.builder().author("Lawrence Kutner MD").type("MRI").date("01/17/2020").build();
    public static final Episode CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE_NOTES = Episode.builder()
            .notes("""
                                        Central Bay Imaging
                    Cervical: C3-4 disc herniation. C4-5 disc herniation. C5-6 disc bulge.""")
            .build();

    public static final Episode CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE_2 = Episode.builder().author("Central Bay Medical & Rehab Center").type("Billing").date("10/23/2020").build();
    public static final Episode CENTRAL_BAY_MEDICAL_AND_REHAB_CENTER_EPISODE_NOTES_2 = Episode.builder()
            .notes("""
                                        CPT/HCPCS From www.cms.gov. Default to Non-facility / Limiting Charge.
                    CPT Code: 99204 Office o/p new mod 45-59 min VA Outpt Facility Rate: $462.01 MC: $168.83 2xMC: $337.66 Charge: $431.94
                    CPT Code: 97112 Neuromuscular reeducation MC: $34.22 2xMC: $68.44 Charge: $182.64
                    CPT Code: 97110 Therapeutic exercises MC: $29.56 2xMC: $59.12 Charge: $160.78""")
            .build();

    public static final Episode BEST_SIDE_MEDICAL_GROUP_EPISODE = Episode.builder().author("Eva Adams MD").type("Medical").date("01/15/2020").build();
    public static final Episode BEST_SIDE_MEDICAL_GROUP_EPISODE_NOTES = Episode.builder()
            .notes("""
                    Normal gait. Neuro intact. Cervical provocative testing positive.
                    Positive Spurling's. Positive straight leg raise.
                    DX: Cervical pain. Right wrist pain. Right shoulder pain. Left knee pain.""")
            .build();

    public static final Episode EPISODE_13153612 = Episode.builder().author("Claimant").date("10/23/2020").build();
    public static final Episode EPISODE_NOTES_13153612 = Episode.builder()
            .notes("""
                    13th Circuit
                    Hillsborough County
                    Pg 13: Single car MVA in 2012. No injury.
                    Pg 14: Single car MVA in 2014. No injury.
                    Pg 17-18: Single car MVA 2016. Does not recall injury.
                    "PAGE 21: ? X-RAY or MRI when doing JUDO at AGE 17.
                    Pg 126: Saw spine specialist from Ohio who discussed surgery""")
            .build();

    public static final Episode SMITH_DEMO_EASTER_EPISODE = Episode.builder()
            .author("Claimant_".concat(generateName()))
            .type("Medical_".concat(generateName()))
            .build();

    private @NonNull String generateName() {
        return RandomStringUtils.random(5, true, false).toUpperCase();
    }

    public static final String DEPOSITION_TYPE = ROBERT_CHASE_EPISODE.getType();
    public static final String MEDICAL_TYPE = BEST_SIDE_MEDICAL_GROUP_EPISODE.getType();
    public static final String MRI_TYPE = BEST_SIDE_MEDICAL_GROUP_SINGLE_EPISODE.getType();
    public static final String LAWERNCE_KUTNER_AUTHOR = BEST_SIDE_MEDICAL_GROUP_SINGLE_EPISODE.getAuthor();
    public static final String BUDDY_BOMBER_AUTHOR = STRONG_HEALTH_EPISODE_2.getAuthor();
    public static final String CHRISTOPHER_JACOBSON_AUTHOR = PAIN_EXPERTS_MR_EPISODE_2.getAuthor();
    public static final String CLAIMANT_AUTHOR = JOHN_SMITH_EPISODE.getAuthor();
}
