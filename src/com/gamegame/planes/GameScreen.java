package com.gamegame.planes;

import implemented.GLScreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import framework.Game;

import framework.Camera2D;

import framework.Input.TouchEvent;
import framework.ParticleSystem;
import framework.SpriteBatcher;
import framework.Math.OverlapTester;
import framework.Math.Rectangle;
import framework.Math.Vector2;

public class GameScreen extends GLScreen {

	int stateID = -1;
	Camera2D guiCam;
	long timer = 0;
	long timer_AI = 0;
	boolean isplaytyping = true;
	List<Island> f = new ArrayList<Island>();
	List<Plane> p = new ArrayList<Plane>();
	boolean isSelOne = false, isSkip = false, isGoToNeXtlEvEl = false;
	boolean isDrawRect = false;
	int parttimer = 0, printTimer = -1;
	boolean showFlight = false;
	int timer_tut = 0;
	boolean stopTyping = false;
	String strfile = "";
	String str2 = "", PopupMins = "";
	FpsCounter count;
	SpriteBatcher batcher;
	PlanesRenderer pr;
	Vector2 touchPoint;
	Vector2 touchPoint2;
	Rectangle planetBounds;
	ParticleSystem ps;
	Boolean isFirstTouch = true, isLastTouch = false;

	public GameScreen(Game game) {

		super(game);
		GameEntity.srcHeight = glGraphics.getHeight();
		GameEntity.srcWidth = glGraphics.getWidth();
		ps = new ParticleSystem();
		count = new FpsCounter();
		touchPoint = new Vector2();
		touchPoint2 = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 1000);
		guiCam = new Camera2D(glGraphics, PlanesRenderer.FRUSTUM_WIDTH,
				PlanesRenderer.FRUSTUM_HEIGHT);
		InputStream is = null;
		String[] strmas = new String[(int) PlanesRenderer.FRUSTUM_HEIGHT];
		try {
			is = game.getFileIO().readAsset("levels/2.txt");
			strmas = LoadFile(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < PlanesRenderer.FRUSTUM_WIDTH; i++) {
			for (int j = 0; j < PlanesRenderer.FRUSTUM_HEIGHT; j++) {
				Character c = strmas[j].charAt(i);
				String sc = c.toString();
				if (Integer.parseInt(sc) > 0) {
					Island island = new Island();
					island.setX(i, glGraphics.getWidth());
					island.setY(PlanesRenderer.FRUSTUM_HEIGHT - j,
							glGraphics.getHeight());
					if (Integer.parseInt(sc) == 3) {
						island.setCiv(0);
						island.setPopulation(5);
						island.setSize();
					} else {
						island.setCiv(Integer.parseInt(sc));
						island.setPopulation(10 * Integer.parseInt(sc));
						island.setSize();
					}
					f.add(island);
				}
			}
		}

		pr = new PlanesRenderer(glGraphics, batcher);
		guiCam.setViewportAndMatrices();
	}

	public String[] LoadFile(InputStream is) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(is));
		String[] out = new String[(int) PlanesRenderer.FRUSTUM_HEIGHT];
		for (int i = 0; i < PlanesRenderer.FRUSTUM_HEIGHT; i++) {
			out[i] = input.readLine();
		}
		return out;
	}

	@Override
	public void update(float deltaTime) {

		// count.logFrame();
		parttimer++;
		ps.update();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		isSelOne = false;
		for (int ev = 0; ev < len; ev++) {

			TouchEvent event = touchEvents.get(ev);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				if (ev == 0) {
					touchPoint.set(event.x, event.y);

					pr.guiCam.touchToWorld(touchPoint);
					for (int i = 0; i < f.size(); i++) {
						Island island = f.get(i);

						planetBounds = new Rectangle(island.getRealXcam(pr.guiCam),
								island.getRealYcam(pr.guiCam), 64 * pr.guiCam.zoom,
								64 * pr.guiCam.zoom);
						if (OverlapTester.pointInRectangle(planetBounds,
								touchPoint)) {
							if (island.getCiv() == 1) {
								island.Select();
								isSelOne = true;
							} else {
								for (int j = 0; j < f.size(); j++) {
									Island island2 = f.get(j);
									if (island2.isSel && (i != j)
											&& island2.getPopulation() > 1) {
										isDrawRect = false;
										Plane plane = new Plane(island2, island);
										plane.setPopulation(island2
												.getPopulation() / 2);
										plane.setCiv(1);
										island2.setPopulation(island2
												.getPopulation() / 2);
										island2.sf = true;
										p.add(plane);
										island2.Unselect();
									}
									if (i == j) {
										island2.Unselect();
									}
								}
							}
						}
					}
					if (!isSelOne) {
						for (int i = 0; i < f.size(); i++) {
							f.get(i).Unselect();
						}
					}
				} else {

				}

			} else if (event.type == TouchEvent.TOUCH_UP) {
				TouchEvent.startZoom = true;
			} else if (event.type == TouchEvent.TOUCH_DRAGGED) {
				if (ev == 0) {
					Vector2 dragPosition = new Vector2(touchPoint);
					touchPoint.set(event.x, event.y);
					Vector2 dp = dragPosition.sub(touchPoint);
					if (len == 1 && !TouchEvent.startZoom) {

						pr.cam.position.add(pr.getFrustumWidth(dp.x),
								-pr.getFrustumHeight(dp.y));
						pr.guiCam.position.add(dp.x, -dp.y);
					}

				} else {
					if (TouchEvent.startZoom) {
						touchPoint2.set(event.x, event.y);
						TouchEvent.startZoomDistance = touchPoint
								.dist(touchPoint2) / glGraphics.getWidth();
						TouchEvent.startZoom = false;
					}
					touchPoint2.set(event.x, event.y);
					float zoom = pr.cam.zoom
							- (TouchEvent.startZoomDistance - touchPoint
									.dist(touchPoint2) / glGraphics.getWidth())
							/ 10;
					if (zoom > 0.1 && zoom < 2) {
						pr.cam.zoom = zoom;

					}
					pr.guiCam.zoom = pr.cam.zoom;
					Log.d("Camzoom", pr.cam.zoom + " ");
				}
			}

		}
		for (int i = 0; i < p.size(); i++) {
			Plane plane = p.get(i);
			plane.fly(deltaTime);
			if (plane.isDestroyed) {
				plane.destroy();
				p.remove(i);
			}
		}
		if (parttimer == 100) {
			parttimer = 0;
			for (int i = 0; i < f.size(); i++) {
				f.get(i).grow();
			}
		}

		timer_AI++;
		int numPlanets = f.size();
		if (timer_AI == 100) {
			int min = 0, st = 0;
			for (int j = 0; j < numPlanets; j++) {
				if (f.get(j).getCiv() != 2) {
					min = j;
					st = min;
					j = numPlanets;
					break;
				}

			}
			for (int j = st; j < numPlanets; j++) { // »щем вражеский остров с
													// минимальным населением
				if (f.get(j).getPopulation() < f.get(min).getPopulation()
						&& f.get(j).getCiv() != 2) {
					min = j;
				}
			}
			boolean isBreak = false;
			for (int i = 0; i < numPlanets; i++) {
				for (int z = 0; z < numPlanets; z++) {
					if (f.get(i).getCiv() == 2 && f.get(z).getCiv() == 2) {
						// »щем сумму 2 х островов(или одного), который может
						// захватить.
						int sum = 0;
						if (i != z) {
							sum = f.get(i).getPopulation() / 2
									+ f.get(z).getPopulation() / 2;
						} else {
							sum = f.get(i).getPopulation() / 2;
						}
						if ((sum > f.get(min).getPopulation())
								&& f.get(i).getCiv() != f.get(min).getCiv()) {
							// ѕосылаем с первого острова
							Plane plane = new Plane(f.get(i), f.get(min));
							plane.setPopulation(f.get(i).getPopulation() / 2);
							plane.setCiv(2);
							f.get(i).setPopulation(plane.getPopulation());
							p.add(plane);
							f.get(i).sf = true;

							// ѕосылаем со второго острова если он другой
							// (иногда почемуто посылаетс€ и с третьего)
							if (i != z) {
								Plane plane2 = new Plane(f.get(z), f.get(min));
								plane2.setPopulation(f.get(z).getPopulation() / 2);
								plane2.setCiv(2);
								f.get(z).setPopulation(plane2.population);
								p.add(plane2);

								f.get(z).sf = true;
							}
							isBreak = true;
							break;
						}
					}
				}
				if (isBreak) {
					break;
				}
			}
			timer_AI = 0;
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		pr.renderObjects(p, f, ps);

		// gl.glEnable(GL10.GL_BLEND);
		// gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
