package org.homemade.elo.services;

import org.homemade.elo.entities.Match;
import org.homemade.elo.entities.Player;
import org.homemade.elo.repo.MatchRepository;
import org.homemade.elo.repo.PlayerRepository;
import org.homemade.elo.util.RankingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchRegistrationService {
    private PlayerRepository playerRepo;
    private MatchRepository matchRepo;
    private RankingProvider rankingProvider;

    @Autowired
    public MatchRegistrationService(final PlayerRepository playerRepo,
                                    final MatchRepository matchRepo,
                                    final RankingProvider rankingProvider) {
        this.playerRepo = playerRepo;
        this.matchRepo = matchRepo;
        this.rankingProvider = rankingProvider;
    }

    public Match registerMatch(Match match) {
        Player winner = this.playerRepo.findOne(match.getWinnerId());
        Player looser = this.playerRepo.findOne(match.getLooserId());
        if (winner != null && looser != null) {
            int newWinnerRank = this.rankingProvider.calculateRank(winner, looser, 1);
            int newLooserRank = this.rankingProvider.calculateRank(looser, winner, 0);
            winner.withRank(newWinnerRank).incrementGames(true);
            looser.withRank(newLooserRank).incrementGames(false);
            this.playerRepo.save(winner);
            this.playerRepo.save(looser);
            this.matchRepo.save(match);
            return match;
        }
        return null;
    }

}
