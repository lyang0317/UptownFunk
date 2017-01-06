package com;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Lifter implements Serializable {

	private String flag = "";
	private LifterState state = LifterState.STOP;
	private int topfloor = 100;
	private int bottomfloor = 1;
	private int currentfloor = 1;
	private int maxnum = 15;
	private int currentnum = 0;
	private int inpeople = 0;
	private int outpeople = 0;
	private LinkedList<Integer> routes = new LinkedList<Integer>();;
	
	public Lifter(String flag){
		this.flag = flag;
	}
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public LifterState getState() {
		return state;
	}

	public void setState(LifterState state) {
		this.state = state;
	}

	public int getTopfloor() {
		return topfloor;
	}
	public void setTopfloor(int topfloor) {
		this.topfloor = topfloor;
	}
	public int getBottomfloor() {
		return bottomfloor;
	}
	public void setBottomfloor(int bottomfloor) {
		this.bottomfloor = bottomfloor;
	}
	public int getCurrentfloor() {
		return currentfloor;
	}
	public void setCurrentfloor(int currentfloor) {
		this.currentfloor = currentfloor;
	}
	public int getMaxnum() {
		return maxnum;
	}
	public void setMaxnum(int maxnum) {
		this.maxnum = maxnum;
	}
	public int getCurrentnum() {
		return currentnum;
	}
	public void setCurrentnum(int currentnum) {
		this.currentnum = currentnum;
	}
	public int getInpeople() {
		return inpeople;
	}
	public void setInpeople(int inpeople) {
		this.inpeople = inpeople;
	}
	public int getOutpeople() {
		return outpeople;
	}
	public void setOutpeople(int outpeople) {
		this.outpeople = outpeople;
	}
	public LinkedList<Integer> getRoutes() {
		return routes;
	}
	public void setRoutes(LinkedList<Integer> routes) {
		this.routes = routes;
	}
	
	public boolean getPeople(int target) {
		boolean flag = false;
		int curfl = this.currentfloor;
		for(int i = 0; i < Math.abs(target-curfl); i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (Lifter.class) {
				if(this.currentfloor < target){
					this.currentfloor++;
				} else if (this.currentfloor > target){
					this.currentfloor--;
				}	
				if(i+1 == Math.abs(target-curfl)){
					if(this.getMaxnum() != this.getCurrentnum()){
						if(this.currentnum == 0){
							this.state = LifterState.STOP;
						}
						this.currentnum++;
						flag = true;
					}
				}
			}
		}
		return flag;
	}
	
	public boolean watchPeople(Person person) {
		makeRoutes(person);
		/*if(isLifterGet(this, person.getInfloor())){
			return getPeople(person.getInfloor());
		}*/
		return false;
	}
	
	/**
	 * 生成电梯路径
	 * @param routes
	 * @param person
	 */
	private synchronized void makeRoutes(Person person) {
		if(this.state==LifterState.UP){
			upAddInRoute(person);
			upAddOutRoute(person);
		} else if (this.state==LifterState.DOWN) {
			downAddInRoute(person);
			downAddOutRoute(person);
		} else if (this.state==LifterState.STOP){
			this.routes.add(person.getInfloor());
			this.routes.add(person.getOutfloor());
		}
			
	}
	
	private void upAddOutRoute(Person person) {
		Iterator<Integer> iterator = this.routes.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			i++;
			boolean flag = false;
			int floor = iterator.next();
			if(floor == person.getInfloor()){
				flag = true;
			}
			if(person.getOutfloor() > person.getInfloor() && floor >= person.getOutfloor() && flag==true){
				break;
			} else if (person.getOutfloor() < person.getInfloor() && floor <= person.getOutfloor() && flag==true){
				break;
			}
		}
		routes.add(i, person.getOutfloor());
	}
	
	private void upAddInRoute(Person person) {
		if(this.currentfloor<person.getInfloor()){
			Iterator<Integer> iterator = this.routes.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				i++;
				Integer floor = iterator.next();
				if(floor>=person.getInfloor()){
					break;
				}
			}
			routes.add(i, person.getInfloor());
		} else if (this.currentfloor>person.getInfloor()){
			Iterator<Integer> iterator = this.routes.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				i++;
				Integer floor = iterator.next();
				if(floor<=person.getInfloor()){
					break;
				}
			}
			routes.add(i, person.getInfloor());
		} else {
			routes.add(0,person.getInfloor());
		}
	}
	
	
	private void downAddOutRoute(Person person) {
		Iterator<Integer> iterator = this.routes.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			i++;
			boolean flag = false;
			int floor = iterator.next();
			if(floor == person.getInfloor()){
				flag = true;
			}
			if(person.getOutfloor() > person.getInfloor() && floor > person.getOutfloor() && flag==true){
				break;
			} else if (person.getOutfloor() < person.getInfloor() && floor < person.getOutfloor() && flag==true){
				break;
			}
		}
		routes.add(i, person.getOutfloor());
	}
	
	private void downAddInRoute(Person person) {
		if(this.currentfloor>person.getInfloor()){
			Iterator<Integer> iterator = this.routes.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				i++;
				Integer floor = iterator.next();
				if(floor<person.getInfloor()){
					break;
				}
			}
			routes.add(i, person.getInfloor());
		} else if (this.currentfloor<person.getInfloor()){
			Iterator<Integer> iterator = this.routes.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				i++;
				Integer floor = iterator.next();
				if(floor>person.getInfloor()){
					break;
				}
			}
			routes.add(i, person.getInfloor());
		}
	}
	
	private boolean isLifterGet(Lifter lifter, int infloor) {
		if((lifter.getMaxnum() != lifter.getCurrentnum())){
			if(((lifter.getCurrentfloor() <= infloor && lifter.getState() == LifterState.UP) || (lifter.getCurrentfloor() >= infloor && lifter.getState() == LifterState.DOWN) || (lifter.getState() == LifterState.STOP))){
				return true;
			}				
		}
		return false;
	}
	
	public void report() {
		while (true) {
			synchronized (Lifter.class) {
				System.out.println("lift:"+this.flag+" has arrived "+this.currentfloor+", current people is :"+this.currentnum+"!");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendPeople(Person person) {
		for (int i = 0; i < Math.abs(person.getOutfloor()-person.getInfloor()); i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(person.getOutfloor()-person.getInfloor() > 0){
				this.currentfloor++;
			} else if (person.getOutfloor()-person.getInfloor() < 0){
				this.currentfloor--;
			}
		}
		this.currentnum--;
	}
	
	@Override
	public String toString() {
		return "Lifter [flag=" + flag + ", state=" + state + ", topfloor="
				+ topfloor + ", bottomfloor=" + bottomfloor + ", currentfloor="
				+ currentfloor + ", maxnum=" + maxnum + ", currentnum="
				+ currentnum + ", inpeople=" + inpeople + ", outpeople="
				+ outpeople + "]";
	}
	
	public synchronized void working() {
		if(this.routes.size()!=0){
			setStateInfo(routes);
		} else {
			this.state = LifterState.STOP;
		}
	}
	private void setStateInfo(LinkedList<Integer> routes) {
		LinkedList<Integer> tempRoutes = (LinkedList<Integer>) routes.clone();
		if(this.currentfloor<this.routes.get(0)){
			this.state = LifterState.UP;
		} else if(this.currentfloor>this.routes.get(0)){
			this.state = LifterState.DOWN;
		} else {
			tempRoutes.remove(0);
			setStateInfo(tempRoutes);
		}
	}
	
}
