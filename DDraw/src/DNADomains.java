

/**
 * This class creates the 4 Domains to be used
 * in the construction of DNA Bricks.
 * Domain 1 and Domain 3 are complementary to each other.
 * Domain 2 and Domain 4 are complementary to each other.
 *
 * The combined length of the Domains is 42.
 * There are two pairs of Domains. Domain 1,2 and Domain 3,4
 * each Pair is of length 21.
 */
public class DNADomains {

    // Define the DNA Strings for the 4 Domains to be used in the construction of DNA Bricks

    public String domainSeqOne;
    public String domainSeqTwo;
    public String domainSeqThree;
    public String domainSeqFour;

    DNASequenceGenerator dnaSequenceGenerator;


    public DNADomains() {
        dnaSequenceGenerator = new DNASequenceGenerator(10);
        domainSeqOne = dnaSequenceGenerator.getRandomDNASeq();

        dnaSequenceGenerator = new DNASequenceGenerator(11);
        domainSeqTwo = dnaSequenceGenerator.getRandomDNASeq();

        this.domainSeqThree = negateSeq(domainSeqOne);
        this.domainSeqFour = negateSeq(domainSeqTwo);
    }

    public String negateSeq(String domainSeq) {
        String negateSeq = "";
        int stringLength = domainSeq.length();

        for (int i = 0; i < stringLength; i++) {
            if (domainSeq.charAt(i) == 'A') {
                negateSeq += "T";
            } else if (domainSeq.charAt(i) == 'T') {
                negateSeq += "A";
            } else if (domainSeq.charAt(i) == 'G') {
                negateSeq += "C";
            } else if (domainSeq.charAt(i) == 'C') {
                negateSeq += "G";
            }
        }
        return negateSeq;
    }

    public String getDomainSeqOne() {
        return domainSeqOne;
    }

    public String getDomainSeqTwo() {
        return domainSeqTwo;
    }

    public String getDomainSeqThree() {
        return domainSeqThree;
    }

    public String getDomainSeqFour() {
        return domainSeqFour;
    }
}

