package res.committee;

import java.util.List;

import res.reservoir.Reservoir;

interface CommitteeOptimization {	
	List<Reservoir> optimize(List<Reservoir> resSet, int remain);
}
